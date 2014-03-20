package com.todoist.mediaparser;

import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

/*
 * See: http://instagram.com/developer/embedding
 */
class InstagramParser extends AbsImageMediaParser {
    private static Pattern sIdPattern;

    InstagramParser(String url) {
        super(url);
    }

    @Override
    protected boolean matches() {
        // Use contains() before super.matches() to avoid creating expensive patterns when it's bound to fail.
        return (mUrl.contains("instagr.am/") || mUrl.contains("instagram.com/")) && super.matches();
    }

    @Override
    protected Size[] getAvailableSizes() {
        return new Size[] {
                new Size("t", 150),
                new Size("m", 306),
                new Size("l", -1)
        };
    }

    @Override
    protected String getUrlTemplate() {
        return "http://instagr.am/p/%1$s/media/?size=%2$s";
    }

    @Override
    protected Pattern getIdPattern() {
        if(sIdPattern == null) {
            sIdPattern = Pattern.compile(
                    "https?://(?:www\\.)?(?:instagr\\.am|instagram\\.com)/p/([\\w-]+)/?",
                    Pattern.CASE_INSENSITIVE
            );
        }
        return sIdPattern;
    }
}
