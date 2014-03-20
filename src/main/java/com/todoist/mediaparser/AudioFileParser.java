package com.todoist.mediaparser;

import com.todoist.mediaparser.util.StringUtils;

class AudioFileParser extends MediaParser {
    // FIXME: .aac is only supported in Android 3.0+.
    // FIXME: .flac is only supported in Android 3.1+.
    private static final String[] EXTENSIONS = {".m4a", ".aac", ".flac", ".mp3", ".mid", ".wav"};

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
    public String getThumbnailUrl(int smallestSide) {
        return null;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public Type getType() {
        return Type.AUDIO;
    }
}