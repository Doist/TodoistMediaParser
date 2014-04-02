package com.todoist.mediaparser.mediaparser;

import com.fasterxml.jackson.core.JsonParser;
import com.todoist.mediaparser.util.MediaType;
import com.todoist.mediaparser.util.Size;

import java.io.IOException;
import java.util.regex.Pattern;

public class SlideShareParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;
	private static Size[] sAvailableSizes;

	SlideShareParser(String url) {
		super(url);
	}

	@Override
	public MediaType getContentMediaType() {
		return MediaType.OTHER;
	}

	@Override
	public boolean isContentDirect() {
		return false;
	}

	@Override
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null) {
			sMatchingPattern = Pattern.compile(
					"https?://(?:www\\.)?slideshare\\.net/[\\w\\-]+/[\\w\\-]+",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		String thumbnailUrl = super.createThumbnailUrl(smallestSide);
		if(thumbnailUrl != null) {
			try {
				JsonParser jsonParser;
				jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
				int thumbnailWidth = Integer.valueOf(getValueForName(jsonParser, "thumbnail_width"));
				jsonParser.close();
				jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
				int thumbnailHeight = Integer.valueOf(getValueForName(jsonParser, "thumbnail_height"));
				jsonParser.close();

				Size size = null;
				if(Math.min(thumbnailWidth, thumbnailHeight) < smallestSide)
					size = Size.getBestSizeForSmallestSide(getAvailableSizes(), smallestSide);

				if(size != null)
					thumbnailUrl = thumbnailUrl.replaceFirst("thumbnail\\.jpg", "thumbnail-" + size.key + ".jpg");
			} catch(MissingValueForNameException | NumberFormatException e) {
				/* Ignore. */
			} catch(IOException e) {
				e.printStackTrace();
			}

			// The "thumbnail" value doesn't include the protocol, it starts with "//".
			return "http:" + thumbnailUrl;
		}
		else {
			return null;
		}
	}

	private Size[] getAvailableSizes() {
		if(sAvailableSizes == null) {
			sAvailableSizes = new Size[] {
					new Size("3", 240),
					new Size("4", 576)
			};
		}
		return sAvailableSizes;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.slideshare.net/api/oembed/2?url=%s&format=json";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail";
	}
}
