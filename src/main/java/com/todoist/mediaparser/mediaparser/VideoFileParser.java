package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.MediaParser;
import com.todoist.mediaparser.util.MediaType;

import java.util.regex.Pattern;

public class VideoFileParser extends MediaParser {
    private static final String[] EXTENSIONS = {".3gp", ".mp4", ".webm", ".mkv"};

    VideoFileParser(String url) {
        super(url);
    }

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		return true; // Always immediate.
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.VIDEO;
	}

	@Override
	public boolean isContentDirect() {
		return true;
	}

	@Override
    protected boolean matches() {
        for(String extension : EXTENSIONS) {
	        if(endsWithIgnoreCase(mUrl, extension))
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

    private boolean endsWithIgnoreCase(String str, String suffix) {
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
