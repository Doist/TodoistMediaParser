package com.todoist.mediaparser.mediaentity;

import java.util.regex.Pattern;

public class ImageFileEntity extends MediaEntity {
    private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};

    ImageFileEntity(String url) {
        super(url);
    }

	@Override
	public boolean matches() {
        for(String extension : EXTENSIONS) {
	        if(endsWithIgnoreCase(mUrl, extension))
		        return true;
        }
        return false;
    }

	@Override
	public boolean isConfigurationBlocking() {
		return false;
	}

	@Override
	public String getThumbnailUrl(int smallestSide) {
		return mUrl;
	}

	@Override
	protected Pattern getMatchingPattern() {
		return null; // matches() is overridden.
	}

	@Override
	protected void doConfigure() {
		mContentUrl = mUrl;
		mContentType = mUnderlyingContentType = "image/*";
	}

	private boolean endsWithIgnoreCase(String str, String suffix) {
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
