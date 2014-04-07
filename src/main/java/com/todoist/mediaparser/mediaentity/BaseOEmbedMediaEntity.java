package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import java.io.IOException;

/*
 * See: http://oembed.com/
 */
abstract class BaseOEmbedMediaEntity extends MediaEntity {
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
	protected void doConfigure(HttpStack httpStack) throws IOException {
		// Ensure there's an HTTP stack.
		httpStack = httpStack != null ? httpStack : getDefaultHttpStack();

		try {
			// Get oEmbed data.
			String oEmbedResponse = httpStack.getResponse(String.format(getOEmbedUrlTemplate(), mUrl));
			if(oEmbedResponse != null) {
				JSONObject oEmbedData = (JSONObject)JSONValue.parse(oEmbedResponse);
				// Gather available info, depending on the type.
				String type = (String)oEmbedData.get("type");

				if("photo".equals(type)) {
					mContentUrl = (String)oEmbedData.get("url");
					mContentType = "image/*";
					mUnderlyingContentType = "image/*";
				}
				else if("video".equals(type)) {
					mUnderlyingContentType = "video/*";
				}

				// Get thumbnail url and size.
				mThumbnailUrl = (String)oEmbedData.get(getOEmbedThumbnailUrlName());
				Integer thumbnailWidth = getAsInteger(oEmbedData, getOEmbedThumbnailWidthName());
				Integer thumbnailHeight = getAsInteger(oEmbedData, getOEmbedThumbnailHeightName());
				if(thumbnailWidth != null && thumbnailHeight != null)
					mThumbnailSmallestSide = Math.min(thumbnailWidth, thumbnailHeight);
			}
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

	private Integer getAsInteger(JSONObject object, String name) {
		Object value = object.get(name);
		if(value instanceof Integer)
			return (Integer)value;
		else if(value instanceof String)
			return Integer.valueOf((String)value);
		return null;
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
