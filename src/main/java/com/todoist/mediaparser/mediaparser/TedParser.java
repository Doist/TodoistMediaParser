package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.Type;

import java.util.regex.Pattern;

public class TedParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;

	TedParser(String url) {
		super(url);
	}

	@Override
	public Type getContentType() {
		return Type.VIDEO;
	}

	@Override
	public boolean isContentDirect() {
		return false;
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
