package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.Type;
import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

public class VimeoParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;
	private static Size[] sAvailableSizes;

	VimeoParser(String url) {
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
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null) {
			sMatchingPattern = Pattern.compile(
					"https?://(?:www\\.)?vimeo\\.com/(?:album/\\w+/video/|groups/\\w+/videos/)?\\w+/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		String thumbnailUrl = super.createThumbnailUrl(smallestSide);

		Size size = null;
		if(thumbnailUrl != null)
			size = Size.getSizeForSmallestSide(getAvailableSizes(), smallestSide);

		if(size != null)
			return thumbnailUrl.replaceAll("_[\\d+]\\.jpg$", "_" + size.key + ".jpg");
		else
			return thumbnailUrl;
	}

	private Size[] getAvailableSizes() {
		if(sAvailableSizes == null) {
			sAvailableSizes = new Size[] {
					new Size("100", 100),
					new Size("200", 200),
					new Size("640", 640),
			};
		}
		return sAvailableSizes;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://vimeo.com/api/oembed.json?url=%s";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}
}
