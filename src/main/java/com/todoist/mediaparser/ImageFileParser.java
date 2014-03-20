package com.todoist.mediaparser;

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
    public String getThumbnailUrl(int smallestSide) {
        return getUrl();
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public Type getType() {
        return Type.IMAGE;
    }

    private boolean endsWithIgnoreCase(String str, String suffix) {
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
