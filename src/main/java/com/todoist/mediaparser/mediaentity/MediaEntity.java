package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import java.util.regex.Pattern;

/**
 * Holds various information about the associated media. It needs to be configured through a call to
 * {@link #configure(HttpStack)}, which can be a blocking call depending on {@link #isConfigurationBlocking()}.
 * No information is available before this is called, except {@link #getUrl()}.
 */
public abstract class MediaEntity {
    protected String mUrl;
    protected boolean mConfigured;
    protected String mContentUrl;
    protected String mContentType;
    protected String mUnderlyingContentType;

    protected MediaEntity(String url) {
        mUrl = url;
    }

    /**
     * Returns the original url.
     */
    public final String getUrl() {
        return mUrl;
    }

    /**
     * Configure this media entity.
     *
     * @param httpStack the HTTP stack to use, falls back to the default one if {@code null}.
     * @see #isConfigured()
     * @see #isConfigurationBlocking()
     */
    public synchronized void configure(HttpStack httpStack) {
        if (!mConfigured) {
            try {
                doConfigure(httpStack);
                mConfigured = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConfigured() {
        return mConfigured;
    }

    public abstract boolean isConfigurationBlocking();

    /**
     * Returns true if this parser is appropriate for {@code mUrl} passed in during instantiation, or false if not.
     */
    public boolean matches() {
        return getMatchingPattern().matcher(mUrl).lookingAt();
    }

    /**
     * Returns the url for this media. Redirects might need to be followed.
     */
    public String getContentUrl() {
        ensureConfigured();
        return mContentUrl;
    }

    /**
     * Returns the content type of the content, in the form of type/subtype. The subtype can be unknown, hence be "*".
     */
    public String getContentType() {
        ensureConfigured();
        return mContentType;
    }

    /**
     * Returns the underlying content type. Useful for non-direct urls which are associated with certain types of
     * media,
     * eg. YouTube URLs will always point to videos.
     */
    public String getUnderlyingContentType() {
        ensureConfigured();
        return mUnderlyingContentType;
    }

    /**
     * Returns an image thumbnail for this media. Redirects might need to be followed.
     *
     * Subclasses should override and provide a thumbnail whose smallest side is equal or larger than
     * {@code smallestSide}, unless it's not available or the size is negative. In those cases, the largest possible
     * thumbnail image should be returned.
     */
    public abstract String getThumbnailUrl(int smallestSide);

    /**
     * Returns a pattern that matches valid urls. Used by {@link #matches()}.
     */
    protected abstract Pattern getMatchingPattern();

    /**
     * Perform the setup. {@code #mContentUrl}, {@code #mContentType} and {@code #mUnderlyingContentType} should all
     * be set.
     *
     * The passed-in {@code httpStack} can be null. If so, and it's needed, use
     * {@link com.todoist.mediaparser.util.SimpleHttpStack}.
     */
    protected abstract void doConfigure(HttpStack httpStack) throws Exception;

    private void ensureConfigured() {
        if (!mConfigured) {
            throw new IllegalStateException("configure() was never called");
        }
    }
}
