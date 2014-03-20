package com.todoist.mediaparser;

import java.util.ArrayList;
import java.util.List;

public abstract class MediaParser {
    private static final List<Class<? extends MediaParser>> MEDIA_PARSERS =
            new ArrayList<Class<? extends MediaParser>>() {{
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

    /**
     * Returns an appropriate {@link MediaParser} instance for this {@code url}, or {code null} if not supported.
     */
    public static MediaParser getInstance(String url) {
        for(Class<? extends MediaParser> mediaParserClass : MEDIA_PARSERS) {
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

    MediaParser(String url) {
        mUrl = url;
    }

    /**
     * Returns true if this parser is appropriate for {@code mUrl}, or false if not.
     */
    protected abstract boolean matches();

    /**
     * Returns an image thumbnail for this media. Redirects might need to be followed. The thumbnail size will be equal
     * to or larger than {@code smallestSide}, unless it's not available or the size is negative. In those cases, the
     * largest possible thumbnail image is returned.
     *
     * This is not guaranteed to be fast, thus should be called from a background thread. Can be null.
     */
    public abstract String getThumbnailUrl(int smallestSide);

    /**
     * Returns the url for this media. Redirects might need to be followed.
     *
     * This is not guaranteed to be fast, thus should be called from a background thread. Cannot be null.
     */
    public abstract String getUrl();

    /**
     * Returns the type of this media.
     */
    public abstract Type getType();


    public enum Type {
        /**
         * An image file link is returned by {@link #getUrl()}.
         */
        IMAGE,

        /**
         * A video file link is returned by {@link #getUrl()}..
         */
        VIDEO,

        /**
         * An audio file link is returned by {@link #getUrl()}.
         */
        AUDIO,

        /**
         * An external link is returned by {@link #getUrl()}.
         */
        EXTERNAL
    }
}
