package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import java.io.IOException;
import java.util.regex.Pattern;

public class HuluEntity extends BaseOEmbedMediaEntity {
    private static Pattern sMatchingPattern;

    HuluEntity(String url) {
        super(url);
    }

    @Override
    protected void doConfigure(HttpStack httpStack) throws IOException {
        super.doConfigure(httpStack);

        if ("text/html".equals(mUnderlyingContentType)) {
            mUnderlyingContentType = "video/*";
        }
    }

    @Override
    protected Pattern getMatchingPattern() {
        if (sMatchingPattern == null) {
            sMatchingPattern =
                    Pattern.compile("https?://(?:www\\.)?hulu\\.com/watch/[\\w\\-/]+", Pattern.CASE_INSENSITIVE);
        }
        return sMatchingPattern;
    }

    @Override
    protected String getOEmbedUrlTemplate() {
        return "http://www.hulu.com/api/oembed?url=%s&format=json";
    }

    @Override
    protected String getOEmbedThumbnailUrlName() {
        return "large_thumbnail_url";
    }

    @Override
    protected String getOEmbedThumbnailWidthName() {
        return "large_thumbnail_width";
    }

    @Override
    protected String getOEmbedThumbnailHeightName() {
        return "large_thumbnail_height";
    }
}
