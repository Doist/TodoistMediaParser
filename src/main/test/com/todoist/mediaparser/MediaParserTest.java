package com.todoist.mediaparser;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class MediaParserTest {
    @Test
    public void testImglyParsing() throws IOException {
        MediaParser mediaParser = MediaParser.getInstance("http://img.ly/ylsL");
        assertThat(mediaParser.getType(), is(MediaParser.Type.IMAGE));
        checkThumbnailAndUrlContentType(mediaParser, 150, ImglyParser.class, "image/");
    }

    @Test
    public void testInstagramParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://instagram.com/p/eQKJG2AClg/");
        assertThat(mediaParser.getType(), is(MediaParser.Type.IMAGE));
        checkThumbnailAndUrlContentType(mediaParser, 100, InstagramParser.class, "image/");

        mediaParser = MediaParser.getInstance("http://instagr.am/p/jxOBBDgCo5");
        assertThat(mediaParser.getType(), is(MediaParser.Type.IMAGE));
        checkThumbnailAndUrlContentType(mediaParser, 200, InstagramParser.class, "image/");
    }

    @Test
    public void testTwitpicParser() throws IOException {
        MediaParser mediaParser = MediaParser.getInstance("http://twitpic.com/dylpf2");
        assertThat(mediaParser.getType(), is(MediaParser.Type.IMAGE));
        checkThumbnailAndUrlContentType(mediaParser, 100, TwitpicParser.class, "image/");
    }

    @Test
    public void testYfrogParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://twitter.yfrog.com/ocre1nbdj");
        assertThat(mediaParser.getType(), is(MediaParser.Type.EXTERNAL));
        checkThumbnailAndUrlContentType(mediaParser, 75, YfrogParser.class, "text/html");

        mediaParser = MediaParser.getInstance("http://yfrog.com/nvvdfdej");
        assertThat(mediaParser.getType(), is(MediaParser.Type.EXTERNAL));
        checkThumbnailAndUrlContentType(mediaParser, 150, YfrogParser.class, "text/html");

        mediaParser = MediaParser.getInstance("http://www.yfrog.com/0ia9mcz");
        assertThat(mediaParser.getType(), is(MediaParser.Type.EXTERNAL));
        checkThumbnailAndUrlContentType(mediaParser, 90, YfrogParser.class, "text/html");
    }

    @Test
    public void testImageFileParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Sego_lily_cm.jpg/225px-Sego_lily_cm.jpg");
        assertThat(mediaParser.getType(), is(MediaParser.Type.IMAGE));
        checkThumbnailAndUrlContentType(mediaParser, 666, ImageFileParser.class, "image/");

        mediaParser = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png");
        assertThat(mediaParser.getType(), is(MediaParser.Type.IMAGE));
        checkThumbnailAndUrlContentType(mediaParser, 666, ImageFileParser.class, "image/");

        mediaParser = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/2/2c/Rotating_earth_%28large%29.gif");
        assertThat(mediaParser.getType(), is(MediaParser.Type.IMAGE));
        checkThumbnailAndUrlContentType(mediaParser, 666, ImageFileParser.class, "image/");
    }

    @Test
    public void testYoutubeParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("https://www.youtube.com/watch?v=9bZkp7q19f0");
        assertThat(mediaParser.getType(), is(MediaParser.Type.EXTERNAL));
        checkThumbnailAndUrlContentType(mediaParser, 666, YoutubeParser.class, "text/html");

        mediaParser = MediaParser.getInstance("http://youtu.be/wcLNteez3c4");
        assertThat(mediaParser.getType(), is(MediaParser.Type.EXTERNAL));
        checkThumbnailAndUrlContentType(mediaParser, 666, YoutubeParser.class, "text/html");
    }

    @Test
    public void testVimeoParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://vimeo.com/album/2642665/video/74622970");
        assertThat(mediaParser.getType(), is(MediaParser.Type.EXTERNAL));
        checkThumbnailAndUrlContentType(mediaParser, 666, VimeoParser.class, "text/html");

        mediaParser = MediaParser.getInstance("http://vimeo.com/67410022");
        assertThat(mediaParser.getType(), is(MediaParser.Type.EXTERNAL));
        checkThumbnailAndUrlContentType(mediaParser, 666, VimeoParser.class, "text/html");
    }

    @Test
    public void testVideoFileParser() throws IOException {
        // This isn't easy to test as raw public videos stored on the web are rare and ephemeral.
    }

    private void checkThumbnailAndUrlContentType(MediaParser mediaParser, int thumbnailSize,
                                                 Class<? extends MediaParser> expectedClass,
                                                 String expectedContentType) throws IOException {
        assertThat(mediaParser, is(instanceOf(expectedClass)));

        URL thumbnailUrl = new URL(mediaParser.getThumbnailUrl(thumbnailSize));
        URLConnection thumbnailConnection = thumbnailUrl.openConnection();
        assertThat(thumbnailConnection.getHeaderField("Content-Type"), containsString("image/")); // Thumbnails are images.

        URL imageUrl = new URL(mediaParser.getUrl());
        URLConnection imageConnection = imageUrl.openConnection();
        assertThat(imageConnection.getHeaderField("Content-Type"), containsString(expectedContentType));
    }
}
