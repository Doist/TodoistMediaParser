package com.todoist.mediaparser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.todoist.mediaparser.util.HttpUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * See: http://oembed.com/
 */
abstract class AbsOEmbedParser extends MediaParser {
	private static final JsonFactory JSON_FACTORY = new JsonFactory();

	protected String mOEmbedResponse; // Cached until mContentUrl and mThumbnailUrl are created.

	AbsOEmbedParser(String url) {
		super(url);
	}

	@Override
	public String getContentUrl() {
		String contentUrl = super.getContentUrl();
		clearOEmbedResponseIfUnecessary();
		return contentUrl;
	}

	@Override
	public String getThumbnailUrl(int smallestSide) {
		mThumbnailSmallestSide = smallestSide; // Never refresh thumbnail, there's only one.
		String thumbnailUrl = super.getThumbnailUrl(smallestSide);
		clearOEmbedResponseIfUnecessary();
		return thumbnailUrl;
	}

	@Override
	protected String createContentUrl() {
		String contentUrl = mUrl;
		if(isContentUrlInOEmbedResponse()) {
			try {
				JsonParser jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
				contentUrl = getContentUrl(jsonParser);
				jsonParser.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return contentUrl;
	}

	@Override
	protected final String createThumbnailUrl(int smallestSide) {
		String thumbnailUrl = null;
		try {
			JsonParser jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
			thumbnailUrl = getThumbnailUrl(jsonParser);
			jsonParser.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return thumbnailUrl;
	}

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		mThumbnailSmallestSide = smallestSide; // Never refresh thumbnail, there's only one.
		return super.isThumbnailImmediate(smallestSide);
	}

	/**
	 * Returns a URL suitable for JSON parsing of this oEmbed content. Will be interpolated with {@code mUrl}.
	 */
	protected abstract String getOEmbedUrlTemplate();

	/**
	 * Returns true if the direct content can be found in the oEmbed response, false if not.
	 */
	protected abstract boolean isContentUrlInOEmbedResponse();

	/**
	 * Returns the content url. Only needs a proper implementation if {@link #isContentUrlInOEmbedResponse()} returns
	 * true, otherwise it's not used.
	 */
	protected abstract String getContentUrl(JsonParser jsonParser) throws IOException;

	/**
	 * Returns the largest available thumbnail url from this {@code jsonParser}. The parser is opened and closed
	 * automatically.
	 */
	protected abstract String getThumbnailUrl(JsonParser jsonParser) throws IOException;

	private String getOEmbedResponse() throws IOException {
		if(mOEmbedResponse == null) {
			URL url = new URL(String.format(getOEmbedUrlTemplate(), mUrl));
			mOEmbedResponse = HttpUtils.readFrom((HttpURLConnection)url.openConnection());
		}
		return mOEmbedResponse;
	}

	private void clearOEmbedResponseIfUnecessary() {
		if((mContentUrl != null || !isContentUrlInOEmbedResponse()) && mThumbnailUrl != null)
			mOEmbedResponse = null;
	}
}
