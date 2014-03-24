package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import java.util.regex.Pattern;

public class FlickrParser extends BaseOEmbedMediaParserWithContent {
	private static Pattern sMatchingPattern;

	FlickrParser(String url) {
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
					"https?://(?:www\\.)?(?:flic\\.kr/p|flickr.com/photos)/\\w+/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		// Flickr thumbnails are tiny. If available, use content url.
		String contentUrl = super.getContentUrl();
		if(!mUrl.equals(contentUrl))
			return contentUrl;
		else
			return super.createThumbnailUrl(smallestSide);
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.flickr.com/services/oembed?url=%s&format=json";
	}

	@Override
	protected String getOEmbedContentUrlName() {
		return "url";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}
}
