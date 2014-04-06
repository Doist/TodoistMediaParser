package com.todoist.mediaparser.mediaentity;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.todoist.mediaparser.util.HttpUtils;

import java.io.IOException;
import java.net.URL;

/*
 * See: http://oembed.com/
 */
abstract class BaseOEmbedMediaEntity extends MediaEntity {
	protected static final JsonFactory JSON_FACTORY = new JsonFactory();

	protected String mThumbnailUrl;
	protected int mThumbnailSmallestSide = Integer.MAX_VALUE;

	BaseOEmbedMediaEntity(String url) {
		super(stripTrailingSlash(url));
	}

	@Override
	public boolean isConfigurationBlocking() {
		return true;
	}

	@Override
	public String getThumbnailUrl(int smallestSide) {
		if(smallestSide <= mThumbnailSmallestSide || !mContentType.startsWith("image/"))
			return mThumbnailUrl;
		else
			return mContentUrl;
	}

	@Override
	protected void doConfigure() throws IOException {
		// Get oEmbed data.
		String oEmbedResponse =
				HttpUtils.readFrom(getHttpClient().open(new URL(String.format(getOEmbedUrlTemplate(), mUrl))));
		JsonParser jsonParser;

		try {
			// Gather available info, depending on the type.
			String oEmbedType = null;
			try {
				jsonParser = JSON_FACTORY.createParser(oEmbedResponse);
				oEmbedType = getValueForName(jsonParser, "type");
				jsonParser.close();
			} catch(MissingValueForNameException e) { /* Ignore */ }

			if("photo".equals(oEmbedType)) {
				try {
					jsonParser = JSON_FACTORY.createParser(oEmbedResponse);
					mContentUrl = getValueForName(jsonParser, "url");
					mContentType = "image/*";
				} catch(MissingValueForNameException e) { /* Ignore */ }
				mUnderlyingContentType = "image/*";
			}
			else if("video".equals(oEmbedType)) {
				mUnderlyingContentType = "video/*";
			}

			// Get thumbnail url and size.
			try {
				jsonParser = JSON_FACTORY.createParser(oEmbedResponse);
				mThumbnailUrl = getValueForName(jsonParser, getOEmbedThumbnailUrlName());
				jsonParser.close();

				jsonParser = JSON_FACTORY.createParser(oEmbedResponse);
				int thumbnailWidth = Integer.valueOf(getValueForName(jsonParser, getOEmbedThumbnailWidthName()));
				jsonParser.close();
				jsonParser = JSON_FACTORY.createParser(oEmbedResponse);
				int thumbnailHeight = Integer.valueOf(getValueForName(jsonParser, getOEmbedThumbnailHeightName()));
				jsonParser.close();
				mThumbnailSmallestSide = Math.min(thumbnailWidth, thumbnailHeight);
			} catch(MissingValueForNameException e) { /* Ignore. */ }
		} catch(IOException e) {
			/* Ignore. */
		}

		// Apply defaults, in case anything is not set or fails.
		if(mContentUrl == null)
			mContentUrl = mUrl;
		if(mContentType == null)
			mContentType = "text/html";
		if(mUnderlyingContentType == null)
			mUnderlyingContentType = "text/html";
	}

	/**
	 * Returns a URL suitable for JSON parsing of this oEmbed content. Will be interpolated with {@code mUrl}.
	 *
	 * The first call is always a blocking call. After that, the result is cached.
	 */
	protected abstract String getOEmbedUrlTemplate();

	/**
	 * Returns the JSON name for the thumbnail url in the oEmbed content. Should point to the largest available
	 * thumbnail.
	 *
	 * Defaults to "thumbnail_url".
	 */
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}

	/**
	 * Returns the JSON name for the thumbnail width in the oEmbed document, the one pointed by
	 * {@link #getOEmbedThumbnailUrlName()}}.
	 *
	 * Defaults to "thumbnail_width".
	 */
	protected String getOEmbedThumbnailWidthName() {
		return "thumbnail_width";
	}

	/**
	 * Returns the JSON name for the thumbnail height in the oEmbed document, the one pointed by
	 * {@link #getOEmbedThumbnailUrlName()}}.
	 *
	 * Defaults to "thumbnail_height".
	 */
	protected String getOEmbedThumbnailHeightName() {
		return "thumbnail_height";
	}

	protected String getValueForName(JsonParser jsonParser, String name) throws IOException {
		if(jsonParser.nextToken() == JsonToken.START_OBJECT) {
			while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String currentName = jsonParser.getCurrentName();
				jsonParser.nextToken(); // Move to the value.
				if(name.equals(currentName))
					return jsonParser.getText();
			}
		}
		throw new MissingValueForNameException(name);
	}

	protected static class MissingValueForNameException extends IOException {
		public MissingValueForNameException(String name) {
			super("No value for '" + name + '"');
		}
	}

	/**
	 * Strip trailing slash, as the url will be used in oEmbed calls and some providers don't like it.
	 */
	private static String stripTrailingSlash(String url) {
		if(url.endsWith("/"))
			return url.substring(0, url.length() - 1);
		else
			return url;
	}
}
