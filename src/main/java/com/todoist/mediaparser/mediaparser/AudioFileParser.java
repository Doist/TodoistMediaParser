package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.MediaParser;
import com.todoist.mediaparser.util.Type;
import com.todoist.mediaparser.util.StringUtils;

import java.util.regex.Pattern;

public class AudioFileParser extends MediaParser {
    private static final String[] EXTENSIONS = {".m4a", ".aac", ".amr", ".flac", ".mp3", ".mid", ".wav"};

    AudioFileParser(String url) {
        super(url);
    }

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		return true; // Always immediate.
	}

	@Override
	public Type getContentType() {
		return Type.AUDIO;
	}

	@Override
	public boolean isContentDirect() {
		return true;
	}

	@Override
    protected boolean matches() {
        for(String extension : EXTENSIONS) {
	        if(StringUtils.endsWithIgnoreCase(mUrl, extension))
		        return true;
        }
        return false;
    }

	@Override
	protected Pattern getMatchingPattern() {
		return null; // matches() is overridden.
	}

    @Override
    protected String createThumbnailUrl(int smallestSide) {
        return null;
    }

    @Override
    protected String createContentUrl() {
        return mUrl;
    }
}