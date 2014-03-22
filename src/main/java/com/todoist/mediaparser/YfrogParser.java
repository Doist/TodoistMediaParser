package com.todoist.mediaparser;

import com.todoist.mediaparser.util.MediaType;
import com.todoist.mediaparser.util.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
 * See: http://yfrog.com/page/api#a5
 */
class YfrogParser extends AbsMediaParserWithId {
    private static final List<Size> AVAILABLE_SIZES = new ArrayList<Size>() {{
        add(new Size("small", 100));
        add(new Size("iphone", 480));
    }};

    private static Pattern sIdPattern;

    YfrogParser(String url) {
        super(url);
    }

    @Override
    protected boolean matches() {
        return mUrl.contains("yfrog.com/") && super.matches();
    }

    @Override
    public String createThumbnailUrl(int smallestSide) {
        Size size = null;

        for(Size availableSize : AVAILABLE_SIZES) {
            if(availableSize.smallestSide >= smallestSide) {
                size = availableSize;
                break;
            }
        }
        if(size == null)
            AVAILABLE_SIZES.get(AVAILABLE_SIZES.size() - 1);

        return String.format("http://yfrog.com/%1$s:%2$s", mId, size.key);
    }

    @Override
    public String createContentUrl() {
        return mUrl;
    }

    @Override
    public MediaType createContentMediaType() {
        return MediaType.OTHER; // Can be IMAGE or VIDEO.
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
}
