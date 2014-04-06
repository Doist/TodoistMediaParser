package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

public class FlickrEntity extends BaseOEmbedMediaEntity {
	private static Pattern sMatchingPattern;
	private static Size[] sAvailableSizes;

	FlickrEntity(String url) {
		super(url);
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
	public String getThumbnailUrl(int smallestSide) {
		if(mThumbnailSmallestSide < smallestSide) {
			Size size = Size.getSizeForSmallestSide(getAvailableSizes(), smallestSide);
			if(size != null) {
				mThumbnailUrl =
						mThumbnailUrl.replaceFirst("_[sqtmnzcbo]\\.((?:jpg)|(?:gif)|(?:png))$", "_" + size.key + ".$1");
				mThumbnailSmallestSide = size.smallestSide;
			}
		}

		return super.getThumbnailUrl(smallestSide);
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.flickr.com/services/oembed?url=%s&format=json";
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
}
