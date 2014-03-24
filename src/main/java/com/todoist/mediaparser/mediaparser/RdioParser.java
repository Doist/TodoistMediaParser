package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import java.util.regex.Pattern;

public class RdioParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;

	RdioParser(String url) {
		super(url);
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.AUDIO;
	}

	@Override
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null) {
			sMatchingPattern = Pattern.compile(
					"https?://(?:www\\.)?rdio\\.com/artist/[\\w\\-/]+",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.rdio.com/api/oembed/?url=%s&format=json";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}
}
