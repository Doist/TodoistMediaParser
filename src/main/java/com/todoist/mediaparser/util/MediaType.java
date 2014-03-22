package com.todoist.mediaparser.util;

import com.todoist.mediaparser.MediaParser;

public enum MediaType {
    /**
     * An image file link is returned by {@link MediaParser#createContentUrl()}.
     */
    IMAGE,

    /**
     * A video file link is returned by {@link MediaParser#createContentUrl()}..
     */
    VIDEO,

    /**
     * An audio file link is returned by {@link MediaParser#createContentUrl()}.
     */
    AUDIO,

    /**
     * An external link is returned by {@link MediaParser#createContentUrl()}.
     */
    EXTERNAL
}
