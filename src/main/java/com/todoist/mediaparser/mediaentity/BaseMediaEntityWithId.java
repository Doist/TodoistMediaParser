package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class BaseMediaEntityWithId extends MediaEntity {
    protected String mId;

    BaseMediaEntityWithId(String url) {
        super(url);
    }

    /**
     * Returns true if the {@link Pattern} created using {@link #getIdPattern()} finds {@link #mId} in {@link #mUrl}.
     */
    @Override
    public boolean matches() {
        return getIdPattern().matcher(mUrl).lookingAt();
    }

	@Override
	protected void doConfigure(HttpStack httpStack) throws Exception {
		Matcher matcher = getIdPattern().matcher(mUrl);
		if(matcher.lookingAt())
			mId = matcher.group(1);
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
