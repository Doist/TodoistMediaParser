package com.todoist.mediaparser.mediaentity;

import java.util.regex.Pattern;

public class DeviantartEntity extends BaseOEmbedMediaEntity {
    private static Pattern sMatchingPattern;

    DeviantartEntity(String url) {
        super(url);
    }

    @Override
    protected Pattern getMatchingPattern() {
        if (sMatchingPattern == null) {
            sMatchingPattern =
                    Pattern.compile(
                            "https?://(?:www\\.)?" +
                                    "(?:[\\w-]+\\.deviantart\\.com/(?:art/|[^/]+#/d)|fav\\.me/|sta\\.sh/)" +
                                    "[\\w-]+/?",
                            Pattern.CASE_INSENSITIVE);
        }
        return sMatchingPattern;
    }

    @Override
    protected String getOEmbedUrlTemplate() {
        return "http://backend.deviantart.com/oembed?url=%s&format=json";
    }
}
