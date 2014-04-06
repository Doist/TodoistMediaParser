package com.todoist.mediaparser.util;

import com.todoist.mediaparser.MediaParser;

public enum Type {
    /**
     * {@link MediaParser#getContentUrl()} is an image.
     */
    IMAGE,

    /**
     * {@link MediaParser#getContentUrl()} is a video.
     */
    VIDEO,

    /**
     * {@link MediaParser#getContentUrl()} is audio.
     */
    AUDIO,

    /**
     * {@link MediaParser#getContentUrl()} is some other type of media, or it's undetermined.
     * For a more accurate reading, check its mime type.
     */
    OTHER
}
