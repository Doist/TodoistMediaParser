package com.todoist.mediaparser.mediaparser;

import com.fasterxml.jackson.core.JsonParser;
import com.todoist.mediaparser.util.MediaType;

import java.io.IOException;
import java.util.regex.Pattern;

public class DeviantartParser extends BaseOEmbedMediaParser {
	private static Pattern sMatchingPattern;

	DeviantartParser(String url) {
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
					"https?://(?:www\\.)?" +
							"(?:[\\w-]+\\.deviantart\\.com/(?:art/|[^/]+#/d)|fav\\.me/|sta\\.sh/)" +
							"[\\w-]+/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String createThumbnailUrl(int smallestSide) {
		try {
			JsonParser jsonParser;
			jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
			int thumbnailWidth = Integer.valueOf(getValueForName(jsonParser, "thumbnail_width"));
			jsonParser.close();
			jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
			int thumbnailHeight = Integer.valueOf(getValueForName(jsonParser, "thumbnail_height"));
			jsonParser.close();

			if(Math.min(thumbnailWidth, thumbnailHeight) < smallestSide) {
				jsonParser = JSON_FACTORY.createParser(getOEmbedResponse());
				String url = getValueForName(jsonParser, "url");
				jsonParser.close();
				if(url != null)
					return url;
			}

			return super.createThumbnailUrl(smallestSide);
		} catch(MissingValueForNameException | NumberFormatException e) {
			/* Ignore. */
		} catch(IOException e) {
			e.printStackTrace();
		}
		return super.createThumbnailUrl(smallestSide);
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://backend.deviantart.com/oembed?url=%s&format=json";
	}

	@Override
	protected String getOEmbedThumbnailUrlName() {
		return "thumbnail_url";
	}
}
