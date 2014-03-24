package com.todoist.mediaparser.mediaparser;

import com.todoist.mediaparser.util.MediaType;
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
	public MediaType getContentMediaType() {
		return MediaType.IMAGE;
	}

    @Override
    public String createThumbnailUrl(int smallestSide) {
        Size size = null;

        Size[] availableSizes = getAvailableSizes();
        for(Size availableSize : availableSizes) {
            if(availableSize.smallestSide >= smallestSide) {
                size = availableSize;
                break;
            }
        }
        if(size == null)
            size = availableSizes[availableSizes.length - 1];

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