package com.todoist.mediaparser.mediaentity;

import java.io.IOException;
import java.util.regex.Pattern;

public class JustinTvEntity extends BaseOEmbedMediaEntity {
	private static Pattern sMatchingPattern;

	JustinTvEntity(String url) {
		super(url);
	}

	@Override
	protected void doConfigure() throws IOException {
		super.doConfigure();

		if("text/html".equals(mUnderlyingContentType))
			mUnderlyingContentType = "video/*";
	}

	@Override
	protected Pattern getMatchingPattern() {
		if(sMatchingPattern == null)
			sMatchingPattern = Pattern.compile("https?://(?:www\\.)?justin\\.tv/[\\w\\-]+/?", Pattern.CASE_INSENSITIVE);
		return sMatchingPattern;
	}

	@Override
	protected String getOEmbedUrlTemplate() {
		return "http://api.justin.tv/api/embed/from_url.json?url=%s";
	}
}
