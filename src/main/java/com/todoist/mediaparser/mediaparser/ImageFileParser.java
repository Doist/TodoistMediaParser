package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.MediaParser;
import com.todoist.mediaparser.util.Type;

import java.util.regex.Pattern;

public class ImageFileParser extends MediaParser {
    private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};

    ImageFileParser(String url) {
        super(url);
    }

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		return true; // Always immediate.
	}

	@Override
	public Type getContentType() {
		return Type.IMAGE;
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
        return createContentUrl();
    }

    @Override
    protected String createContentUrl() {
        return mUrl;
    }

    private boolean endsWithIgnoreCase(String str, String suffix) {
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
