package com.todoist.mediaparser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.todoist.mediaparser.util.MediaType;

import java.io.IOException;
import java.util.regex.Pattern;

public class FlickrParser extends AbsOEmbedParser {
	private static Pattern sPattern;

	FlickrParser(String url) {
		super(url);
	}

	@Override
	protected boolean matches() {
		return (mUrl.contains("flic.kr/p/") || mUrl.contains("flickr.com/photos/"))
				&& getPattern().matcher(mUrl).lookingAt();
	}

	@Override
	protected MediaType getContentMediaType() {
		return MediaType.OTHER;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://www.flickr.com/services/oembed?url=%s&format=json";
	}

	@Override
	protected boolean isContentUrlInOEmbedResponse() {
		return true;
	}

	@Override
	protected String getContentUrl(JsonParser jsonParser) throws IOException {
		if(jsonParser.nextToken() == JsonToken.START_OBJECT) {
			while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String name = jsonParser.getCurrentName();
				jsonParser.nextToken(); // Move to the value.
				if("url".equals(name))
					return jsonParser.getText();
			}
		}
		throw new IOException("url field not found in oEmbed");
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
					"https?://(?:www\\.)?(?:(?:flic\\.kr/p)|(?:flickr.com/photos))/\\w+/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sPattern;
	}
}
