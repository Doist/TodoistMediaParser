package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

/*
 * See: http://dev.twitpic.com/docs/thumbnails/ and http://dev.twitpic.com/docs/2/faces_create/ (inside "Description")
 */
public class TwitpicParser extends BaseImageMediaParser {
    private static Pattern sIdPattern;

    TwitpicParser(String url) {
        super(url);
    }

    @Override
    protected Size[] getAvailableSizes() {
        return new Size[] {
                new Size("mini", 75),
                new Size("thumb", 150),
                new Size("large", -1)
        };
    }

    @Override
    protected String getUrlTemplate() {
        return "http://twitpic.com/show/%2$s/%1$s";
    }

    @Override
    protected Pattern getIdPattern() {
        if(sIdPattern == null)
            sIdPattern = Pattern.compile("https?://(?:www\\.)?twitpic\\.com/(\\w+)/?", Pattern.CASE_INSENSITIVE);
        return sIdPattern;
    }
}