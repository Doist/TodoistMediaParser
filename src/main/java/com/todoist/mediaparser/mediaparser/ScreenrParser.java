package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import java.util.regex.Pattern;

public class ScreenrParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;

	ScreenrParser(String url) {
		super(url);
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.VIDEO;
	}

	@Override
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null)
			sMatchingPattern = Pattern.compile("https?://(?:www\\.)?screenr\\.com/\\w+/?", Pattern.CASE_INSENSITIVE);
		return sMatchingPattern;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		String thumbnailUrl = super.createThumbnailUrl(smallestSide);
		if(smallestSide > 110)
			return thumbnailUrl.replaceFirst("_thumb\\.jpg$", ".jpg");
		else
			return thumbnailUrl;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.screenr.com/api/oembed.json?url=%s";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}
}
