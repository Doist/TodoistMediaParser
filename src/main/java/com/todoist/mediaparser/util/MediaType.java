package com.todoist.mediaparser.util;

import com.todoist.mediaparser.MediaParser;

public enum MediaType {
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
     */
    OTHER
}