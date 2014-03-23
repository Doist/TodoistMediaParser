package com.todoist.mediaparser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.todoist.mediaparser.util.MediaType;

import java.io.IOException;
import java.util.regex.Pattern;

public class YoutubeParser extends AbsOEmbedParser {
	private static Pattern sPattern;

	YoutubeParser(String url) {
		super(url);
	}

	@Override
	protected boolean matches() {
		return (mUrl.contains("youtube.com/") || mUrl.contains("youtu.be/")) && getPattern().matcher(mUrl).lookingAt();
	}

	@Override
	protected MediaType getContentMediaType() {
		return MediaType.VIDEO;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.youtube.com/oembed?url=%s&format=json";
	}

	@Override
	protected String getThumbnailUrl(JsonParser jsonParser) throws IOException {
		if(jsonParser.nextToken() == JsonToken.START_OBJECT) {
			while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String name = jsonParser.getCurrentName();
				jsonParser.nextToken(); // Move to the value.
				if("thumbnail_url".equals(name))
					return jsonParser.getText();
			}
		}
		return null;
	}

	private Pattern getPattern() {
		if(sPattern == null) {
			sPattern = Pattern.compile(
					"https?://(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)[\\w-]+/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sPattern;
	}
}
