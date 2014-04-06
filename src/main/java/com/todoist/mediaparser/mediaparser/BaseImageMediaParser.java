package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.Type;
import com.todoist.mediaparser.util.Size;

abstract class BaseImageMediaParser extends BaseMediaParserWithId {
    BaseImageMediaParser(String url) {
        super(url);
    }

	@Override
	public boolean isThumbnailImmediate(int smallestSide) {
		return true; // Always immediate.
	}

	@Override
	public Type getContentType() {
		return Type.IMAGE;
	}

	@Override
	public boolean isContentDirect() {
		return true;
	}

	@Override
    public String createThumbnailUrl(int smallestSide) {
	    Size size = Size.getBestSizeForSmallestSide(getAvailableSizes(), smallestSide);
	    return String.format(getUrlTemplate(), mId, size.key);
    }

	@Override
    protected String createContentUrl() {
        Size[] availableSizes = getAvailableSizes();
        return String.format(getUrlTemplate(), mId, availableSizes[availableSizes.length - 1].key);
    }

    /**
     * Returns an ordered list of {@link Size}s, with the first being the smallest and the last being the largest (full)
     * size. All {@link Size}s must have a fixed {@link Size#smallestSide}, except for the last one, as it can be
     * omitted as it's the largest version.
     */
    protected abstract Size[] getAvailableSizes();

    /**
     * Returns a template to be used with {@link String#format(String, Object...)} where the first argument is
     * {@link #mId} and the second argument is {@link Size#key}.
     */
    protected abstract String getUrlTemplate();
}
