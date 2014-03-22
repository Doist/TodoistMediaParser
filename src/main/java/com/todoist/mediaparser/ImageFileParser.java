package com.todoist.mediaparser;

import com.todoist.mediaparser.util.MediaType;

class ImageFileParser extends MediaParser {
    // FIXME: .webp is only supported in Android 4.0+.
    private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};

    ImageFileParser(String url) {
        super(url);
    }

    @Override
    protected boolean matches() {
        boolean matches = false;
        for(String extension : EXTENSIONS)
            matches |= endsWithIgnoreCase(mUrl, extension);
        return matches;
    }

    @Override
    public String createThumbnailUrl(int smallestSide) {
        return createContentUrl();
    }

    @Override
    public String createContentUrl() {
        return mUrl;
    }

    @Override
    public MediaType createContentMediaType() {
        return MediaType.IMAGE;
    }

    private boolean endsWithIgnoreCase(String str, String suffix) {
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
