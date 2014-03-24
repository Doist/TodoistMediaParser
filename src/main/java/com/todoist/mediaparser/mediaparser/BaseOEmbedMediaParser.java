package com.todoist.mediaparser.mediaparser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.todoist.mediaparser.MediaParser;
import com.todoist.mediaparser.util.HttpUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * See: http://oembed.com/
 */
abstract class BaseOEmbedMediaParser extends MediaParser {
	protected static final JsonFactory JSON_FACTORY = new JsonFactory();

	BaseOEmbedMediaParser(String url) {
		super(url);
	}

	@Override
	public String getThumbnailUrl(int smallestSide) {
		mThumbnailSmallestSide = smallestSide; // Never refresh thumbnail, there's only one.
		return super.getThumbnailUrl(smallestSide);
	}

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		mThumbnailSmallestSide = smallestSide; // Never refresh thumbnail, there's only one.
		return super.isThumbnailImmediate(smallestSide);
	}

	@Override
	protected String createContentUrl() {
		return mUrl;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		String thumbnailUrl = null;
		try {
			JsonParser jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
			thumbnailUrl = getValueForName(jsonParser, getOEmbedThumbnailUrlName());
			jsonParser.close();
		} catch(MissingValueForNameException e) {
			/* Ignore. */
		} catch(IOException e) {
			e.printStackTrace();
		}
		return thumbnailUrl;
	}

	/**
	 * Returns a URL suitable for JSON parsing of this oEmbed content. Will be interpolated with {@code mUrl}.
	 */
	protected abstract String getOEmbedUrlTemplate();

	/**
	 * Returns the JSON name for the thumbnail url in the oEmbed content. Should point to the largest available
	 * thumbnail.
	 */
	protected abstract String getOEmbedThumbnailUrlName();

	protected String getOEmbedResponse() throws IOException {
		URL url = new URL(String.format(getOEmbedUrlTemplate(), mUrl));
		return HttpUtils.readFrom((HttpURLConnection)url.openConnection());
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
}
