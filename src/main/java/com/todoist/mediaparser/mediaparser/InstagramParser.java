package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

/*
 * See: http://instagram.com/developer/embedding
 */
public class InstagramParser extends BaseImageMediaParser {
	private static Pattern sIdPattern;
	private static Size[] sSizes;

	InstagramParser(String url) {
		super(url);
	}

	@Override
	protected Size[] getAvailableSizes() {
		if(sSizes == null) {
			sSizes = new Size[]{
					new Size("t", 150),
					new Size("m", 306),
					new Size("l", -1)
			};
		}
		return sSizes;
	}

	@Override
	protected String getUrlTemplate() {
		return "http://instagr.am/p/%1$s/media/?size=%2$s";
	}

	@Override
	protected Pattern getIdPattern() {
		if(sIdPattern == null) {
			sIdPattern = Pattern.compile(
					"https?://(?:www\\.)?(?:instagr\\.am|instagram\\.com)/p/([\\w-]+)/?",
					Pattern.CASE_INSENSITIVE
			);
		}
		return sIdPattern;
	}
}
