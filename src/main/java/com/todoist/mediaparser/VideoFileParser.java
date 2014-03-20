package com.todoist.mediaparser;

class VideoFileParser extends MediaParser {
    // FIXME: .mp4 H.264 is only supported on Android 3.0+. H.263 is fine.
    // FIXME: .mkv is only supported on Android 4.0+.
    private static final String[] EXTENSIONS = {".3gp", ".mp4", ".webm", ".mkv"};

    VideoFileParser(String url) {
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
        return Type.VIDEO;
    }

    private boolean endsWithIgnoreCase(String str, String suffix) {
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
