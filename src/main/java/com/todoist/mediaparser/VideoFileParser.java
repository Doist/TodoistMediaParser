package com.todoist.mediaparser;

import com.todoist.mediaparser.util.MediaType;

class VideoFileParser extends MediaParser {
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
    public String createThumbnailUrl(int smallestSide) {
        return null;
    }

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		return true; // It's always immediate.
	}

    @Override
    public String createContentUrl() {
        return mUrl;
    }

    @Override
    public MediaType getContentMediaType() {
        return MediaType.VIDEO;
    }

    private boolean endsWithIgnoreCase(String str, String suffix) {
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
