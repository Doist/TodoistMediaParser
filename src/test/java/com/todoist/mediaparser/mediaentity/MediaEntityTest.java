package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.MediaParser;
import com.todoist.mediaparser.util.HttpStack;
import com.todoist.mediaparser.util.SimpleHttpStack;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class MediaEntityTest {
	private HttpStack mHttpStack = new SimpleHttpStack();

	@Test
	public void testImglyParsing() throws IOException {
		MediaEntity mediaEntity = MediaParser.getInstance("http://img.ly/ylsL");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, ImglyEntity.class, 150);
	}

	@Test
	public void testInstagramParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://instagram.com/p/eQKJG2AClg/");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, InstagramEntity.class, 100);

		mediaEntity = MediaParser.getInstance("http://instagr.am/p/jxOBBDgCo5");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, InstagramEntity.class, 200);
	}

	@Test
	public void testTwitpicParser() throws IOException {
		MediaEntity mediaEntity = MediaParser.getInstance("http://twitpic.com/dylpf2");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, TwitpicEntity.class, 100);
	}

	@Test
	public void testFlickrParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://www.flickr.com/photos/mwb-photos/13335437595/in/explore-2014-03-22");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, FlickrEntity.class, 1000);

		mediaEntity = MediaParser.getInstance("http://flic.kr/p/hTVoH1");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, FlickrEntity.class, 400);

		mediaEntity = MediaParser.getInstance("https://flic.kr/p/mmGuhg");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, FlickrEntity.class, 245);
	}

	@Test
	public void testYfrogParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://twitter.yfrog.com/ocre1nbdj");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, YfrogEntity.class, 75);

		mediaEntity = MediaParser.getInstance("http://yfrog.com/nvvdfdej");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, YfrogEntity.class, 200);

		mediaEntity = MediaParser.getInstance("http://www.yfrog.com/0ia9mcz");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, YfrogEntity.class, 400);
	}

	@Test
	public void testDeviantartParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://www.deviantart.com/art/Growing-Bird-441918288");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, DeviantartEntity.class, 50);

		mediaEntity = MediaParser.getInstance("http://fav.me/d4klbrc");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, DeviantartEntity.class, 300);

		mediaEntity = MediaParser.getInstance("http://sta.sh/0xhhdd19ax3");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, DeviantartEntity.class, 600);
	}

	@Test
	public void testImageFileParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Sego_lily_cm.jpg/225px-Sego_lily_cm.jpg");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, ImageFileEntity.class, 666);

		mediaEntity = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, ImageFileEntity.class, 666);

		mediaEntity = MediaParser.getInstance("http://upload.wikimedia.org/wikipedia/commons/2/2c/Rotating_earth_%28large%29.gif");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, ImageFileEntity.class, 666);
	}

	@Test
	public void testYoutubeParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("https://www.youtube.com/watch?v=9bZkp7q19f0");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, YoutubeEntity.class, 666);

		mediaEntity = MediaParser.getInstance("http://youtu.be/wcLNteez3c4");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, YoutubeEntity.class, 666);
	}

	@Test
	public void testVimeoParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://vimeo.com/67410022");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, VimeoEntity.class, 90);

		mediaEntity = MediaParser.getInstance("http://vimeo.com/album/2642665/video/74622970");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, VimeoEntity.class, 700);

		mediaEntity = MediaParser.getInstance("http://vimeo.com/groups/shortfilms/videos/85347833");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, VimeoEntity.class, 1500);
	}

	@Test
	public void testHuluParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://www.hulu.com/watch/609104");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, HuluEntity.class, 666);

		mediaEntity = MediaParser.getInstance("http://hulu.com/watch/20807/late-night-with-conan-obrien-wed-may-21-2008");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, HuluEntity.class, 666);
	}

	@Test
	public void testJustinTvParser() throws IOException {
		MediaEntity mediaEntity = MediaParser.getInstance("http://www.justin.tv/deepellumonair");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, JustinTvEntity.class, 666);
	}

	@Test
	public void testScreenrParser() throws IOException {
		MediaEntity mediaEntity = MediaParser.getInstance("http://www.screenr.com/NTHH");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, ScreenrEntity.class, 666);
	}

	@Test
	public void testTedParser() throws IOException {
		MediaEntity mediaEntity = MediaParser.getInstance("http://www.ted.com/talks/ken_robinson_says_schools_kill_creativity");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, TedEntity.class, 666);
	}

	@Test
	public void testVideoFileParser() {
		// Not easy to test as raw public videos stored on the web are rare and ephemeral.
	}

	@Test
	public void testRdioParser() throws IOException {
		MediaEntity mediaEntity = MediaParser.getInstance("http://www.rdio.com/artist/The_Black_Keys/album/Brothers/");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, RdioEntity.class, 666);
	}

	@Test
	public void testSpotifyParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("https://open.spotify.com/track/6zKRBLEnVQoBw76yR1BPDj");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, SpotifyEntity.class, 666);

		mediaEntity = MediaParser.getInstance("https://play.spotify.com/artist/4WN5naL3ofxrVBgFpguzKo");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, SpotifyEntity.class, 666);
	}

	@Test
	public void testSoundCloudParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("https://soundcloud.com/sizzlebird");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, SoundCloudEntity.class, 666);

		mediaEntity = MediaParser.getInstance("http://soundcloud.com/djguesse/amtrac-came-along-guesse-remix");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, SoundCloudEntity.class, 666);
	}

	@Test
	public void testAudioFileParser() {
		// Not easy to test as raw audio files stored on the web are rare and ephemeral.
	}

	@Test
	public void testSlideShareParser() throws IOException {
		MediaEntity mediaEntity;

		mediaEntity = MediaParser.getInstance("http://www.slideshare.net/goncalossilva/ruby-an-introduction");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, SlideShareEntity.class, 230);

		mediaEntity = MediaParser.getInstance("http://www.slideshare.net/intelleto/leading-lean-managing-lean-ux-work-in-the-enterprise-mx-2014-conference-by-adaptive-path");
		mediaEntity.configure(null);
		checkThumbnailAndUrlContentType(mediaEntity, SlideShareEntity.class, 500);
	}

	@Test
	public void testRegisterMediaParser() {
		MediaParser.registerMediaParser(MyServiceMediaEntity.class);

		MediaEntity mediaEntity = MediaParser.getInstance("http://myservice.com");
		mediaEntity.configure(null);
		assertThat(mediaEntity, is(instanceOf(MyServiceMediaEntity.class)));

		MediaParser.unregisterMediaParser(MyServiceMediaEntity.class); // Cleanup.
	}

	@Test
	public void testUnregisterMediaParser() {
		MediaParser.unregisterMediaParser(YoutubeEntity.class);

		MediaEntity mediaEntity = MediaParser.getInstance("http://www.youtube.com/watch?v=9bZkp7q19f0");
		assertThat(mediaEntity, is(nullValue()));

		MediaParser.registerMediaParser(YoutubeEntity.class); // Cleanup.
	}

	private void checkThumbnailAndUrlContentType(MediaEntity mediaEntity,
	                                             Class<? extends MediaEntity> expectedClass,
	                                             int thumbnailSize) throws IOException {
		assertThat(mediaEntity, is(instanceOf(expectedClass)));

		Map<String, String> thumbnailUrlHeaders = mHttpStack.getHeaders(mediaEntity.getThumbnailUrl(thumbnailSize));
		assertThat(thumbnailUrlHeaders.get("Content-Type"), containsString("image/")); // Thumbnails should be images.

		String contentType = mediaEntity.getContentType();
		contentType = contentType.replaceAll("/\\*$", ""); // Remove asterisk to use containsString(String).
		Map<String, String> contentHeaders = mHttpStack.getHeaders(mediaEntity.getContentUrl());
		assertThat(contentHeaders.get("Content-Type"), containsString(contentType));
	}

	private static class MyServiceMediaEntity extends MediaEntity {
		MyServiceMediaEntity(String url) {
			super(url);
		}

		@Override
		public boolean isConfigurationBlocking() {
			return false;
		}

		@Override
		public String getThumbnailUrl(int smallestSide) {
			return null;
		}

		@Override
		protected void doConfigure(HttpStack httpStack) throws Exception {
			// Do nothing.
		}

		@Override
		protected Pattern getMatchingPattern() {
			return Pattern.compile("https?://myservice\\.com");
		}
	}
}
