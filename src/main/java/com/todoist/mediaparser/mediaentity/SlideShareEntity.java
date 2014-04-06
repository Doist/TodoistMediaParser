package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.Size;

import java.io.IOException;
import java.util.regex.Pattern;

public class SlideShareEntity extends BaseOEmbedMediaEntity {
	private static Pattern sMatchingPattern;
	private static Size[] sAvailableSizes;

	SlideShareEntity(String url) {
		super(url);
	}

	@Override
	public String getThumbnailUrl(int smallestSide) {
		if(mThumbnailSmallestSide < smallestSide) {
			Size size = Size.getBestSizeForSmallestSide(getAvailableSizes(), smallestSide);
			if(size != null) {
				mThumbnailUrl = mThumbnailUrl.replaceFirst("thumbnail(?:-\\d)?\\.jpg", "thumbnail-" + size.key + ".jpg");
				mThumbnailSmallestSide = size.smallestSide;
			}
		}

		return super.getThumbnailUrl(smallestSide);
	}

	@Override
	protected void doConfigure() throws IOException {
		super.doConfigure();

		if(mThumbnailUrl != null)
			mThumbnailUrl = "http:" + mThumbnailUrl; // Fix absent protocol.
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
	protected String getOEmbedUrlTemplate() {
		return "http://www.slideshare.net/api/oembed/2?url=%s&format=json";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail";
	}

	private Size[] getAvailableSizes() {
		if(sAvailableSizes == null) {
			sAvailableSizes = new Size[] {
					new Size("3", 240),
					new Size("4", 576)
			};
		}
		return sAvailableSizes;
	}
}
