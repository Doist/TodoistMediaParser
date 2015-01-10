package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import java.io.IOException;
import java.util.regex.Pattern;

public class YoutubeEntity extends BaseOEmbedMediaEntity {
    private static Pattern sMatchingPattern;

    YoutubeEntity(String url) {
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
                    Pattern.compile(
                            "https?://(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)[\\w-]+/?",
                            Pattern.CASE_INSENSITIVE);
        }
        return sMatchingPattern;
    }

    @Override
    protected String getOEmbedUrlTemplate() {
        return "http://www.youtube.com/oembed?url=%s&format=json";
    }
}
