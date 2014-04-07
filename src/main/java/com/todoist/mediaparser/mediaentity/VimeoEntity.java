package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;
import com.todoist.mediaparser.util.Size;

import java.io.IOException;
import java.util.regex.Pattern;

public class VimeoEntity extends BaseOEmbedMediaEntity {
	private static Pattern sMatchingPattern;
	private static Size[] sAvailableSizes;

	VimeoEntity(String url) {
		super(url);
	}

	@Override
	protected void doConfigure(HttpStack httpStack) throws IOException {
		super.doConfigure(httpStack);

		if("text/html".equals(mUnderlyingContentType))
			mUnderlyingContentType = "video/*";
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
	public String getThumbnailUrl(int smallestSide) {
		if(mThumbnailSmallestSide < smallestSide) {
			Size size = Size.getSizeForSmallestSide(getAvailableSizes(), smallestSide);
			if(size != null) {
				mThumbnailUrl = mThumbnailUrl.replaceAll("_[\\d+]\\.jpg$", "_" + size.key + ".jpg");
				mThumbnailSmallestSide = size.smallestSide;
			}
		}

		return super.getThumbnailUrl(smallestSide);
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://vimeo.com/api/oembed.json?url=%s";
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
}
