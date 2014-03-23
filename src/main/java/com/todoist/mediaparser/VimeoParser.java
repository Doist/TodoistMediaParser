package com.todoist.mediaparser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.todoist.mediaparser.util.MediaType;

import java.io.IOException;
import java.util.regex.Pattern;

class VimeoParser extends AbsOEmbedParser {
	private static Pattern sPattern;

	VimeoParser(String url) {
		super(url);
	}

	@Override
	protected boolean matches() {
		return mUrl.contains("vimeo.com/") && getPattern().matcher(mUrl).lookingAt();
	}

	@Override
	protected MediaType getContentMediaType() {
		return MediaType.VIDEO;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://vimeo.com/api/oembed.json?url=%s";
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
					"https?://(?:www\\.)?vimeo\\.com/(?:(?:album/\\w+/video/)|(?:groups/\\w+/videos/))?\\w+/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sPattern;
	}
}
