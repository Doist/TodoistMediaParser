package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import java.io.IOException;
import java.util.regex.Pattern;

/*
 * See: https://twitter.com/nicklas2k/status/330094611202723840
 */
public class SpotifyEntity extends BaseOEmbedMediaEntity {
    private static Pattern sMatchingPattern;

    SpotifyEntity(String url) {
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
                    Pattern.compile(
                            "https?://(?:www|open|play)\\.?spotify\\.com/(?:artist|track)/[\\w\\-/]+",
                            Pattern.CASE_INSENSITIVE);
        }
        return sMatchingPattern;
    }

    @Override
    protected String getOEmbedUrlTemplate() {
        return "https://embed.spotify.com/oembed/?url=%s&format=json";
    }
}
