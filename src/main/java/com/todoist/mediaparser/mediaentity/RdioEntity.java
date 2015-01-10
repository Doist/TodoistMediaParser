package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import java.io.IOException;
import java.util.regex.Pattern;

public class RdioEntity extends BaseOEmbedMediaEntity {
    private static Pattern sMatchingPattern;

    RdioEntity(String url) {
        super(url);
    }

    @Override
    protected void doConfigure(HttpStack httpStack) throws IOException {
        super.doConfigure(httpStack);

        if ("text/html".equals(mUnderlyingContentType)) {
            mUnderlyingContentType = "audio/*";
        }
    }

    @Override
    protected Pattern getMatchingPattern() {
        if (sMatchingPattern == null) {
            sMatchingPattern =
                    Pattern.compile("https?://(?:www\\.)?rdio\\.com/artist/[\\w\\-/]+", Pattern.CASE_INSENSITIVE);
        }
        return sMatchingPattern;
    }

    @Override
    protected String getOEmbedUrlTemplate() {
        return "http://www.rdio.com/api/oembed/?url=%s&format=json";
    }
}
