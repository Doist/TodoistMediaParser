package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;
import com.todoist.mediaparser.util.StringUtils;

import java.util.regex.Pattern;

public class AudioFileEntity extends MediaEntity {
    private static final String[] EXTENSIONS = {".m4a", ".aac", ".amr", ".flac", ".mp3", ".mid", ".wav"};

    AudioFileEntity(String url) {
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
	protected void doConfigure(HttpStack httpStack) {
		mContentUrl = mUrl;
		mContentType = mUnderlyingContentType = "audio/*";
	}
}