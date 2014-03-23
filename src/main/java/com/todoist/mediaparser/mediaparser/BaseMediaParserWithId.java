package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.MediaParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class BaseMediaParserWithId extends MediaParser {
    protected String mId;

    BaseMediaParserWithId(String url) {
        super(url);
    }

    /**
     * Returns true if the {@link Pattern} created using {@link #getIdPattern()} finds {@link #mId} in {@link #mUrl}.
     */
    @Override
    protected boolean matches() {
        Matcher matcher = getIdPattern().matcher(mUrl);
        if(matcher.lookingAt()) {
            mId = matcher.group(1);
	        return true;
        }
        return false;
    }

	@Override
	protected Pattern getMatchingPattern() {
		return null; // matches() is overridden.
	}

	/**
     * Returns a {@link Pattern} that captures the id from {@link #mUrl} in the first group. If possible, this should
     * be cached in a static variable.
     */
    protected abstract Pattern getIdPattern();
}
