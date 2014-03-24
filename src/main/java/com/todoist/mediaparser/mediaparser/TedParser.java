package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import java.util.regex.Pattern;

public class TedParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;

	TedParser(String url) {
		super(url);
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.VIDEO;
	}

	@Override
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null) {
			sMatchingPattern = Pattern.compile(
					"https?://(?:www\\.)?ted\\.com/talks/[\\w]+",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.ted.com/talks/oembed.json?url=%s";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}
}
