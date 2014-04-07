package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;

import java.io.IOException;
import java.util.regex.Pattern;

public class SoundCloudEntity extends BaseOEmbedMediaEntity {
	private static Pattern sMatchingPattern;

	SoundCloudEntity(String url) {
		super(url);
	}

	@Override
	protected void doConfigure(HttpStack httpStack) throws IOException {
		super.doConfigure(httpStack);

		if("text/html".equals(mUnderlyingContentType))
			mUnderlyingContentType = "audio/*";
	}

	@Override
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null) {
			sMatchingPattern = Pattern.compile(
					"https?://(?:www\\.)?soundcloud\\.com/[\\w\\-/]+",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sMatchingPattern;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "https://soundcloud.com/oembed?url=%s&format=json";
	}
}
