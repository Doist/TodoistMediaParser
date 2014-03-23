package com.todoist.mediaparser;

import com.todoist.mediaparser.util.MediaType;
import com.todoist.mediaparser.util.MimeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
	protected MediaType mContentType;
	protected String mContentMimeType;
	protected String mThumbnailMimeType;

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
	 * Returns an image thumbnail for this media. Redirects might need to be followed. The thumbnail size will be equal
	 * to or larger than {@code smallestSide}, unless it's not available or the size is negative. In those cases, the
	 * largest possible thumbnail image is returned.
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
	 * Returns the media type of the content.
	 */
	public final MediaType getContentMediaType() {
		if(mContentType == null)
			mContentType = createContentMediaType();
		return mContentType;
	}

	/**
	 * Returns the mime type of the content.
	 */
	public final String getContentMimeType() {
		if(mContentMimeType == null)
			mContentMimeType = getMimeTypeFor(getContentUrl());
		return mContentMimeType;
	}

	/**
	 * Returns the mime type of thumbnail for {@code smallestSide}.
	 */
	public final String getThumbnailMimeType(int smallestSide) {
		if(mThumbnailMimeType == null)
			mThumbnailMimeType = getMimeTypeFor(getThumbnailUrl(smallestSide));
		return mThumbnailMimeType;
	}

	private String getMimeTypeFor(String urlString) {
		String mimeType = MimeUtils.guessMimeTypeFromExtension(MimeUtils.getExtension(urlString));
		if(mimeType == null) {
			InputStream in = null;
			try {
				in = new URL(urlString).openStream();
				mimeType = MimeUtils.guessContentTypeFromStream(in);
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(in != null)
						in.close();
				} catch(IOException e) {
						/* Ignore. */
				}
			}
		}
		return mimeType;
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
	 * Returns the type of the content.
	 * @see #getContentMediaType().
	 */
	protected abstract MediaType createContentMediaType();
}
