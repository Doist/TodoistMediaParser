package com.todoist.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import java.util.LinkedHashSet;

public abstract class MediaParser {
    private static final LinkedHashSet<Class<? extends MediaParser>> sMediaParsers =
            new LinkedHashSet<Class<? extends MediaParser>>() {{
                add(ImglyParser.class);
                add(InstagramParser.class);
                add(TwitpicParser.class);
                add(YfrogParser.class);
                add(ImageFileParser.class);
                add(YoutubeParser.class);
                add(VimeoParser.class);
                add(VideoFileParser.class);
                add(AudioFileParser.class);
            }};

	protected String mUrl;
    protected String mContentUrl;
	protected String mThumbnailUrl;
	protected int mThumbnailSmallestSide;

    /**
     * Returns an appropriate {@link MediaParser} instance for this {@code url}, or {code null} if not supported.
     */
    public static MediaParser getInstance(String url) {
        for(Class<? extends MediaParser> mediaParserClass : sMediaParsers) {
            try {
                MediaParser mediaParser = mediaParserClass.getDeclaredConstructor(String.class).newInstance(url);
                if(mediaParser.matches())
                    return mediaParser;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

	/**
	 * Registers a {@link MediaParser} subclass. It will be checked when {@link #getInstance(String)} is invoked.
	 *
	 * @return true if it was registered, false if it was already registered.
	 */
	public static boolean registerMediaParser(Class<? extends MediaParser> mediaParserClass) {
		return sMediaParsers.add(mediaParserClass);
	}

	/**
	 * Unregisters a {@link MediaParser} subclass. It will no longer be checked when {@link #getInstance(String)} is
	 * invoked.
	 *
	 * @return true if it was removed, false if it was not registered.
	 */
	public static boolean unregisterMediaParser(Class<? extends MediaParser> mediaParserClass) {
		return sMediaParsers.remove(mediaParserClass);
	}

    MediaParser(String url) {
        mUrl = url;
    }

	/**
	 * Returns the original url.
	 */
	public final String getUrl() {
		return mUrl;
	}

	/**
	 * Returns the url for this media. Redirects might need to be followed.
	 */
	public final String getContentUrl() {
		if(mContentUrl == null)
			mContentUrl = createContentUrl();
		return mContentUrl;
	}

	/**
	 * Returns an image thumbnail for this media. Redirects might need to be followed. The thumbnail smallest side will
	 * be equal to or larger than {@code smallestSide}, unless it's not available or the size is negative. In those
	 * cases, the largest possible thumbnail image is returned.
	 *
	 * This is not guaranteed to be fast, thus should be called from a background thread. Can be null.
	 */
	public final String getThumbnailUrl(int smallestSide) {
		if(mThumbnailUrl == null || mThumbnailSmallestSide != smallestSide) {
			mThumbnailUrl = createThumbnailUrl(smallestSide);
			mThumbnailSmallestSide = smallestSide;
		}
		return mThumbnailUrl;
	}

	/**
	 * Returns true if this parser is able to get a thumbnail immediately, false if not.
	 */
	public boolean isThumbnailImmediate(int smallestSide) {
		return mThumbnailUrl != null && mThumbnailSmallestSide == smallestSide;
	}

	/**
	 * Returns true if this parser is appropriate for {@code mUrl}, or false if not.
	 */
	protected abstract boolean matches();

	/**
	 * Returns a thumbnail url, or null if one isn't found.
	 * @see #getThumbnailUrl(int).
	 */
    protected abstract String createThumbnailUrl(int smallestSide);

	/**
	 * Returns the url for the content.
	 * @see #getContentUrl().
	 */
	protected abstract String createContentUrl();

	/**
	 * Returns the media type of the content.
	 */
	protected abstract MediaType getContentMediaType();
}
