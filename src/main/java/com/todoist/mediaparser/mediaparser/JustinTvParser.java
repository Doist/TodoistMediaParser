package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import java.util.regex.Pattern;

public class JustinTvParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;

	JustinTvParser(String url) {
		super(url);
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.VIDEO;
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
		if(sMatchingPattern == null)
			sMatchingPattern = Pattern.compile("https?://(?:www\\.)?justin\\.tv/[\\w\\-]+/?", Pattern.CASE_INSENSITIVE);
		return sMatchingPattern;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://api.justin.tv/api/embed/from_url.json?url=%s";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}
}
