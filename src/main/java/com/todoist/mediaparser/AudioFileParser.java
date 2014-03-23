package com.todoist.mediaparser;

import com.todoist.mediaparser.util.MediaType;
import com.todoist.mediaparser.util.StringUtils;

class AudioFileParser extends MediaParser {
    private static final String[] EXTENSIONS = {".m4a", ".aac", ".amr", ".flac", ".mp3", ".mid", ".wav"};

    AudioFileParser(String url) {
        super(url);
    }

    @Override
    protected boolean matches() {
        boolean matches = false;
        for(String extension : EXTENSIONS)
            matches |= StringUtils.endsWithIgnoreCase(mUrl, extension);
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
    public MediaType createContentMediaType() {
        return MediaType.AUDIO;
    }
}