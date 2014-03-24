package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;
import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

/*
 * See: http://yfrog.com/page/api#a5
 */
public class YfrogParser extends BaseMediaParserWithId {
    private static Pattern sIdPattern;
	private static Size[] sSizes;

    YfrogParser(String url) {
        super(url);
    }

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		return true; // Always immediate.
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.OTHER; // Can be IMAGE or VIDEO.
	}

	@Override
	protected String createContentUrl() {
		return mUrl;
	}

    @Override
    protected String createThumbnailUrl(int smallestSide) {
        Size size = null;

	    Size[] availableSizes = getAvailableSizes();
        for(Size availableSize : availableSizes) {
            if(availableSize.smallestSide >= smallestSide) {
                size = availableSize;
                break;
            }
        }
        if(size == null)
            size = availableSizes[availableSizes.length - 1];

        return String.format("http://yfrog.com/%1$s:%2$s", mId, size.key);
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

	private Size[] getAvailableSizes() {
		if(sSizes == null) {
			sSizes = new Size[]{
					new Size("small", 100),
					new Size("iphone", 480)
			};
		}
		return sSizes;
	}
}
