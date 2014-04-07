package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;
import com.todoist.mediaparser.util.SimpleHttpStack;
import com.todoist.mediaparser.util.Size;

import java.util.Map;
import java.util.regex.Pattern;

/*
 * See: http://yfrog.com/page/api#a5
 */
public class YfrogEntity extends BaseMediaEntityWithId {
    private static Pattern sIdPattern;
	private static Size[] sPhotoSizes;
	private static Size[] sVideoSizes;

	private String mThumbnailUrl;
	private int mThumbnailSmallestSide;

    YfrogEntity(String url) {
        super(url);
    }

	@Override
	public boolean isConfigurationBlocking() {
		return true;
	}

	@Override
	public String getThumbnailUrl(int smallestSide) {
		if(mThumbnailUrl == null || mThumbnailSmallestSide < smallestSide) {
			if("image/*".equals(mUnderlyingContentType) || "video/*".equals(mUnderlyingContentType)) {
				boolean isImage = "image/*".equals(mUnderlyingContentType);

				Size[] availableSizes = isImage ? getAvailableImageSizes() : getAvailableVideoSizes();
				Size size = Size.getBestSizeForSmallestSide(availableSizes, smallestSide);

				mThumbnailUrl = String.format(getUrlTemplate(), mId, size.key);
				mThumbnailSmallestSide = size.smallestSide;
			}
		}
		return mThumbnailUrl;
	}

	@Override
	protected void doConfigure(HttpStack httpStack) throws Exception {
		super.doConfigure(httpStack);

		// Ensure there's an HTTP stack.
		httpStack = httpStack != null ? httpStack : new SimpleHttpStack();

		// Determine content type using the response headers.
		Map<String, String> headers = httpStack.getHeaders(String.format(getUrlTemplate(), mId, "iphone"));
		String contentType = headers.get("Content-Type");
		boolean isImage = contentType == null || contentType.contains("image/");

		mContentUrl = mUrl;
		mContentType = "text/html";
		mUnderlyingContentType = isImage ? "image/*" : "video/*";
	}

    @Override
    protected Pattern getIdPattern() {
        if(sIdPattern == null) {
            sIdPattern = Pattern.compile(
                    "https?://(?:www\\.)?(?:\\w+\\.)?yfrog\\.com/(\\w+)/?",
                    Pattern.CASE_INSENSITIVE
            );
        }
        return sIdPattern;
    }

	private String getUrlTemplate() {
		return "http://yfrog.com/%1$s:%2$s";
	}

	private Size[] getAvailableImageSizes() {
		if(sPhotoSizes == null) {
			sPhotoSizes = new Size[]{
					new Size("small", 100),
					new Size("medium", 640)
			};
		}
		return sPhotoSizes;
	}

	private Size[] getAvailableVideoSizes() {
		if(sVideoSizes == null) {
			sVideoSizes = new Size[]{
					new Size("small", 100),
					new Size("frame", 640)
			};
		}
		return sVideoSizes;
	}
}
