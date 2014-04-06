package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.Type;
import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

public class FlickrParser extends BaseOEmbedMediaParserWithContent {
	private static Pattern sMatchingPattern;
	private static Size[] sAvailableSizes;

	private boolean mContentDirect = false;

	FlickrParser(String url) {
		super(url);
	}

	@Override
	public Type getContentType() {
		return Type.OTHER;
	}

	@Override
	public boolean isContentDirect() {
		return mContentDirect;
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
	protected String createContentUrl() {
		String contentUrl = super.createContentUrl();
		mContentDirect = !mUrl.equals(contentUrl);
		return contentUrl;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		String thumbnailUrl = super.createThumbnailUrl(smallestSide);
		Size size = Size.getSizeForSmallestSide(getAvailableSizes(), smallestSide);
		if(size != null) {
			return thumbnailUrl.replaceFirst("_[sqtmnzcbo]\\.((?:jpg)|(?:gif)|(?:png))$", "_" + size.key + ".$1");
		}
		else {
			// No thumbnail or none is large enough. Use content if it's a direct link.
			String contentUrl = super.getContentUrl();
			if(!mUrl.equals(contentUrl))
				return contentUrl;
			else
				return thumbnailUrl;
		}
	}

	private Size[] getAvailableSizes() {
		if(sAvailableSizes == null) {
			sAvailableSizes = new Size[] {
					new Size("t", 75),
					new Size("m", 150),
					new Size("n", 240),
					new Size("z", 550),
			};
		}
		return sAvailableSizes;
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
