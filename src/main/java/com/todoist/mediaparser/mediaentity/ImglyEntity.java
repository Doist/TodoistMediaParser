package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.Size;

import java.util.regex.Pattern;

/*
 * See: http://img.ly/api
 */
public class ImglyEntity extends BaseImageMediaEntity {
	private static Pattern sIdPattern;
	private static Size[] sSizes;

	ImglyEntity(String url) {
		super(url);
	}

	@Override
	protected Size[] getAvailableSizes() {
		if(sSizes == null) {
			sSizes = new Size[]{
					new Size("mini", 75),
					new Size("thumb", 150),
					new Size("medium", 240),
					new Size("large", 550),
					new Size("full", -1)
			};
		}
		return sSizes;
	}

	@Override
	protected String getUrlTemplate() {
		return "http://img.ly/show/%2$s/%1$s";
	}

	@Override
	protected Pattern getIdPattern() {
		if(sIdPattern == null)
			sIdPattern = Pattern.compile("https?://(?:www\\.)?img\\.ly/(\\w+)/?");
		return sIdPattern;
	}
}
