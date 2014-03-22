package com.todoist.mediaparser;

import com.todoist.mediaparser.util.MediaType;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MediaParserTest {
    @Test
    public void testImglyParsing() throws IOException {
        MediaParser mediaParser = MediaParser.getInstance("http://img.ly/ylsL");
        checkThumbnailAndUrlContentType(mediaParser, ImglyParser.class, 150, MediaType.IMAGE, "image/", "image/");
    }

    @Test
    public void testInstagramParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://instagram.com/p/eQKJG2AClg/");
        checkThumbnailAndUrlContentType(mediaParser, InstagramParser.class, 100, MediaType.IMAGE, "image/", "image/");

        mediaParser = MediaParser.getInstance("http://instagr.am/p/jxOBBDgCo5");
        checkThumbnailAndUrlContentType(mediaParser, InstagramParser.class, 200, MediaType.IMAGE, "image/", "image/");
    }

    @Test
    public void testTwitpicParser() throws IOException {
        MediaParser mediaParser = MediaParser.getInstance("http://twitpic.com/dylpf2");
        checkThumbnailAndUrlContentType(mediaParser, TwitpicParser.class, 100, MediaType.IMAGE, "image/", "image/");
    }

    @Test
    public void testYfrogParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://twitter.yfrog.com/ocre1nbdj");
        checkThumbnailAndUrlContentType(mediaParser, YfrogParser.class, 75, MediaType.EXTERNAL, "text/html", "text/html");

        mediaParser = MediaParser.getInstance("http://yfrog.com/nvvdfdej");
        checkThumbnailAndUrlContentType(mediaParser, YfrogParser.class, 150, MediaType.EXTERNAL, "text/html", "text/html");

        mediaParser = MediaParser.getInstance("http://www.yfrog.com/0ia9mcz");
        checkThumbnailAndUrlContentType(mediaParser, YfrogParser.class, 90, MediaType.EXTERNAL, "text/html", "text/html");
    }

    @Test
    public void testImageFileParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Sego_lily_cm.jpg/225px-Sego_lily_cm.jpg");
        checkThumbnailAndUrlContentType(mediaParser, ImageFileParser.class, 666, MediaType.IMAGE, "image/", "image/");

        mediaParser = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png");
        checkThumbnailAndUrlContentType(mediaParser, ImageFileParser.class, 666, MediaType.IMAGE, "image/", "image/");

        mediaParser = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/2/2c/Rotating_earth_%28large%29.gif");
        checkThumbnailAndUrlContentType(mediaParser, ImageFileParser.class, 666, MediaType.IMAGE, "image/", "image/");
    }

    @Test
    public void testYoutubeParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("https://www.youtube.com/watch?v=9bZkp7q19f0");
        checkThumbnailAndUrlContentType(mediaParser, YoutubeParser.class, 666, MediaType.EXTERNAL, "text/html", "text/html");

        mediaParser = MediaParser.getInstance("http://youtu.be/wcLNteez3c4");
        checkThumbnailAndUrlContentType(mediaParser, YoutubeParser.class, 666, MediaType.EXTERNAL, "text/html", "text/html");
    }

    @Test
    public void testVimeoParser() throws IOException {
        MediaParser mediaParser;

        mediaParser = MediaParser.getInstance("http://vimeo.com/album/2642665/video/74622970");
        checkThumbnailAndUrlContentType(mediaParser, VimeoParser.class, 666, MediaType.EXTERNAL, "text/html", "text/html");

        mediaParser = MediaParser.getInstance("http://vimeo.com/67410022");
        checkThumbnailAndUrlContentType(mediaParser, VimeoParser.class, 666, MediaType.EXTERNAL, "text/html", "text/html");
    }

    @Test
    public void testVideoFileParser() {
        // This isn't easy to test as raw public videos stored on the web are rare and ephemeral.
    }

	@Test
	public void testAudioFileParser() {

	}

    private void checkThumbnailAndUrlContentType(MediaParser mediaParser, Class<? extends MediaParser> expectedClass,
                                                 int thumbnailSize, MediaType expectedContentMediaType,
                                                 String expectedContentType, String expectedContentMimeType)
		    throws IOException {
        assertThat(mediaParser, is(instanceOf(expectedClass)));

        URL thumbnailUrl = new URL(mediaParser.getThumbnailUrl(thumbnailSize));
        URLConnection thumbnailConnection = thumbnailUrl.openConnection();
	    // Thumbnails are images.
        assertThat(thumbnailConnection.getHeaderField("Content-Type"), containsString("image/"));
	    assertThat(mediaParser.getThumbnailMimeType(thumbnailSize), containsString("image/"));

        URL contentUrl = new URL(mediaParser.getContentUrl());
        URLConnection imageConnection = contentUrl.openConnection();
        assertThat(imageConnection.getHeaderField("Content-Type"), containsString(expectedContentType));
	    assertThat(mediaParser.getContentMimeType(), containsString(expectedContentMimeType));
	    assertThat(mediaParser.getContentMediaType(), is(expectedContentMediaType));
    }
}
