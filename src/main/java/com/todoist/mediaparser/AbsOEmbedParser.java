package com.todoist.mediaparser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.todoist.mediaparser.util.HttpUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * See: http://oembed.com/
 */
abstract class AbsOEmbedParser extends MediaParser {
	private static final JsonFactory JSON_FACTORY = new JsonFactory();

	protected String mThumbnailUrl;

	AbsOEmbedParser(String url) {
		super(url);
	}

	@Override
	protected String createContentUrl() {
		return mUrl;
	}

	@Override
	protected final String createThumbnailUrl(int smallestSide) {
		if(mThumbnailUrl == null) {
			try {
				URL url = new URL(String.format(getOEmbedUrlTemplate(), mUrl));
				String response = HttpUtils.readFrom((HttpURLConnection)url.openConnection());
				JsonParser jsonParser = JSON_FACTORY.createParser(response);
				mThumbnailUrl = getThumbnailUrl(jsonParser);
				jsonParser.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		return mThumbnailUrl;
	}

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		return mThumbnailUrl != null;
	}

	/**
	 * Returns a URL suitable for JSON parsing of this oEmbed content. Will be interpolated with {@code mUrl}.
	 */
	protected abstract String getOEmbedUrlTemplate();

	/**
	 * Returns the largest available thumbnail url from this {@code jsonParser}. The parser is opened and closed
	 * automatically.
	 */
	protected abstract String getThumbnailUrl(JsonParser jsonParser) throws IOException;
}
