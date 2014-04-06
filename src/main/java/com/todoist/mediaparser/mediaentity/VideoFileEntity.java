package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.StringUtils;

import java.util.regex.Pattern;

public class VideoFileEntity extends MediaEntity {
    private static final String[] EXTENSIONS = {".3gp", ".mp4", ".webm", ".mkv"};

    VideoFileEntity(String url) {
        super(url);
    }

	@Override
	public boolean matches() {
        for(String extension : EXTENSIONS) {
	        if(StringUtils.endsWithIgnoreCase(mUrl, extension))
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
		return null;
	}

	@Override
	protected Pattern getMatchingPattern() {
		return null; // matches() is overridden.
	}

	@Override
	protected void doConfigure() {
		mContentUrl = mUrl;
		mContentType = mUnderlyingContentType = "video/*";
	}
}
