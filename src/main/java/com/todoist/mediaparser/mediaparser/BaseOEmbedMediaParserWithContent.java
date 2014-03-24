package com.todoist.mediaparser.mediaparser;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

abstract class BaseOEmbedMediaParserWithContent extends BaseOEmbedMediaParser {
	protected String mOEmbedResponse; // Cached until mContentUrl and mThumbnailUrl are created.

	BaseOEmbedMediaParserWithContent(String url) {
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
		String thumbnailUrl = super.getThumbnailUrl(smallestSide);
		clearOEmbedResponseIfUnecessary();
		return thumbnailUrl;
	}

	@Override
	protected String createContentUrl() {
		String contentUrl = super.createContentUrl();
		try {
			JsonParser jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
			contentUrl = getValueForName(jsonParser, getOEmbedContentUrlName());
			jsonParser.close();
		} catch(MissingValueForNameException e) {
			/* Ignore. */
		} catch(IOException e) {
			e.printStackTrace();
		}
		return contentUrl;
	}

	@Override
	protected String getOEmbedResponse() throws IOException {
		if(mOEmbedResponse == null)
			mOEmbedResponse = super.getOEmbedResponse();
		return mOEmbedResponse;
	}

	private void clearOEmbedResponseIfUnecessary() {
		if(mContentUrl != null && mThumbnailUrl != null)
			mOEmbedResponse = null;
	}

	/**
	 * Returns the JSON name for the content url in the oEmbed content. If not found, will fallback to
	 * {@link BaseOEmbedMediaParser#createContentUrl()}.
	 */
	protected abstract String getOEmbedContentUrlName();
}
