package com.todoist.mediaparser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.todoist.mediaparser.util.MediaType;

import java.io.IOException;
import java.util.regex.Pattern;

public class DeviantartParser extends AbsOEmbedParser {
	private static Pattern sPattern;

	DeviantartParser(String url) {
		super(url);
	}

	@Override
	protected boolean matches() {
		return (mUrl.contains("deviantart.com/") || mUrl.contains("fav.me/") || mUrl.contains("sta.sh/"))
				&& getPattern().matcher(mUrl).lookingAt();
	}

	@Override
	protected MediaType getContentMediaType() {
		return MediaType.OTHER;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://backend.deviantart.com/oembed?url=%s&format=json";
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
			System.out.println("Getting pattern!");
			sPattern = Pattern.compile(
					"https?://(?:www\\.)?" +
							"(?:(?:[\\w-]+\\.deviantart\\.com/(?:(?:art/)|(?:[^/]+#/d)))|(?:fav\\.me/)|(?:sta\\.sh/))" +
							"[\\w-]+/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sPattern;
	}
}
