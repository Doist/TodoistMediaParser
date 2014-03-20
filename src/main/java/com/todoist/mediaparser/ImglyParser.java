package com.todoist.mediaparser;

import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

/*
 * See: http://img.ly/api
 */
class ImglyParser extends AbsImageMediaParser {
    ImglyParser(String url) {
        super(url);
    }

    @Override
    protected boolean matches() {
        // Use contains() before super.matches() to avoid creating expensive patterns when it's bound to fail.
        return mUrl.contains("img.ly/") && super.matches();
    }

    @Override
    protected Size[] getAvailableSizes() {
        return new Size[] {
                new Size("mini", 75),
                new Size("thumb", 150),
                new Size("medium", 240),
                new Size("large", 550),
                new Size("full", -1)
        };
    }

    @Override
    protected String getUrlTemplate() {
        return "http://img.ly/show/%2$s/%1$s";
    }

    private static Pattern sIdPattern;
    @Override
    protected Pattern getIdPattern() {
        if(sIdPattern == null)
            sIdPattern = Pattern.compile("https?://(?:www\\.)?img\\.ly/(\\w+)/?");
        return sIdPattern;
    }
}
