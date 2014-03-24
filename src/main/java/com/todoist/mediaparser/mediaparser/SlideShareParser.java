package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import java.util.regex.Pattern;

public class SlideShareParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;

	SlideShareParser(String url) {
		super(url);
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.OTHER;
	}

	@Override
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null) {
			sMatchingPattern = Pattern.compile(
					"https?://(?:www\\.)?slideshare\\.net/[\\w\\-]+/[\\w\\-]+",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		// The "thumbnail" value doesn't include the protocol, it starts with "//".
		String thumbnailUrl = super.createThumbnailUrl(smallestSide);
		return thumbnailUrl != null ? "http:" + thumbnailUrl : null;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.slideshare.net/api/oembed/2?url=%s&format=json";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail";
	}
}
