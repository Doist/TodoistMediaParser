package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;
import com.todoist.mediaparser.util.Size;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

/*
 * See: http://yfrog.com/page/api#a5
 */
public class YfrogParser extends BaseMediaParserWithId {
    private static Pattern sIdPattern;
	private static Size[] sPhotoSizes;
	private static Size[] sVideoSizes;

    YfrogParser(String url) {
        super(url);
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

	    boolean isImage = true;
	    try {
		    URLConnection connection = getHttpClient().open(new URL(String.format(getUrlTemplate(), mId, "iphone")));
		    String contentType = connection.getHeaderField("Content-Type");
		    isImage = contentType == null || contentType.contains("image/");
	    } catch(IOException e) {
		    /* Ignore. */
	    }

	    Size[] availableSizes = isImage ? getAvailableImageSizes() : getAvailableVideoSizes();
	    for(Size availableSize : availableSizes) {
            if(availableSize.smallestSide >= smallestSide) {
                size = availableSize;
                break;
            }
        }
        if(size == null)
            size = availableSizes[availableSizes.length - 1];

        return String.format(getUrlTemplate(), mId, size.key);
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
