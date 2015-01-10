package com.todoist.mediaparser;

import com.todoist.mediaparser.mediaentity.AudioFileEntity;
import com.todoist.mediaparser.mediaentity.DeviantartEntity;
import com.todoist.mediaparser.mediaentity.FlickrEntity;
import com.todoist.mediaparser.mediaentity.HuluEntity;
import com.todoist.mediaparser.mediaentity.ImageFileEntity;
import com.todoist.mediaparser.mediaentity.ImglyEntity;
import com.todoist.mediaparser.mediaentity.InstagramEntity;
import com.todoist.mediaparser.mediaentity.JustinTvEntity;
import com.todoist.mediaparser.mediaentity.MediaEntity;
import com.todoist.mediaparser.mediaentity.RdioEntity;
import com.todoist.mediaparser.mediaentity.ScreenrEntity;
import com.todoist.mediaparser.mediaentity.SlideShareEntity;
import com.todoist.mediaparser.mediaentity.SoundCloudEntity;
import com.todoist.mediaparser.mediaentity.SpotifyEntity;
import com.todoist.mediaparser.mediaentity.TedEntity;
import com.todoist.mediaparser.mediaentity.TwitpicEntity;
import com.todoist.mediaparser.mediaentity.VideoFileEntity;
import com.todoist.mediaparser.mediaentity.VimeoEntity;
import com.todoist.mediaparser.mediaentity.YfrogEntity;
import com.todoist.mediaparser.mediaentity.YoutubeEntity;

import java.lang.reflect.Constructor;
import java.util.LinkedHashSet;

public abstract class MediaParser {
    private static final LinkedHashSet<Class<? extends com.todoist.mediaparser.mediaentity.MediaEntity>> sMediaParsers =
            new LinkedHashSet<Class<? extends MediaEntity>>() {{
                add(ImglyEntity.class);
                add(InstagramEntity.class);
                add(TwitpicEntity.class);
                add(FlickrEntity.class);
                add(YfrogEntity.class);
                add(DeviantartEntity.class);
                add(ImageFileEntity.class);
                add(YoutubeEntity.class);
                add(VimeoEntity.class);
                add(HuluEntity.class);
                add(JustinTvEntity.class);
                add(ScreenrEntity.class);
                add(TedEntity.class);
                add(VideoFileEntity.class);
                add(RdioEntity.class);
                add(SpotifyEntity.class);
                add(SoundCloudEntity.class);
                add(AudioFileEntity.class);
                add(SlideShareEntity.class);
            }};

    /**
     * Returns an appropriate {@link MediaEntity} instance for this {@code url}, or {code null} if not supported.
     */
    public static MediaEntity getInstance(String url) {
        for (Class<? extends MediaEntity> mediaParserClass : sMediaParsers) {
            try {
                Constructor<? extends MediaEntity> mediaParserConstructor =
                        mediaParserClass.getDeclaredConstructor(String.class);
                mediaParserConstructor.setAccessible(true);
                MediaEntity mediaEntity = mediaParserConstructor.newInstance(url);
                if (mediaEntity.matches()) {
                    return mediaEntity;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Registers a {@link MediaEntity} subclass. It will be checked when {@link #getInstance(String)} is invoked.
     *
     * @return true if it was registered, false if it was already registered.
     */
    public static boolean registerMediaParser(Class<? extends MediaEntity> mediaParserClass) {
        return sMediaParsers.add(mediaParserClass);
    }

    /**
     * Unregisters a {@link MediaEntity} subclass. It will no longer be checked when {@link #getInstance(String)} is
     * invoked.
     *
     * @return true if it was removed, false if it was not registered.
     */
    public static boolean unregisterMediaParser(Class<? extends MediaEntity> mediaParserClass) {
        return sMediaParsers.remove(mediaParserClass);
    }
}
