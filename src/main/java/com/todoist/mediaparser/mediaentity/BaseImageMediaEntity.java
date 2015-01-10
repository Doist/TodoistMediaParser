package com.todoist.mediaparser.mediaentity;

import com.todoist.mediaparser.util.HttpStack;
import com.todoist.mediaparser.util.Size;

abstract class BaseImageMediaEntity extends BaseMediaEntityWithId {
    private String mThumbnailUrl;
    private int mThumbnailSmallestSide;

    BaseImageMediaEntity(String url) {
        super(url);
    }

    @Override
    public boolean isConfigurationBlocking() {
        return false;
    }

    @Override
    public String getContentType() {
        return "image/*";
    }

    @Override
    public String getThumbnailUrl(int smallestSide) {
        if (mThumbnailUrl == null || mThumbnailSmallestSide != smallestSide) {
            Size size = Size.getBestSizeForSmallestSide(getAvailableSizes(), smallestSide);
            mThumbnailUrl = String.format(getUrlTemplate(), mId, size.key);
            mThumbnailSmallestSide = smallestSide;
        }
        return mThumbnailUrl;
    }

    @Override
    protected void doConfigure(HttpStack httpStack) throws Exception {
        super.doConfigure(httpStack);

        Size[] availableSizes = getAvailableSizes();
        mContentUrl = String.format(getUrlTemplate(), mId, availableSizes[availableSizes.length - 1].key);
        mContentType = mUnderlyingContentType = "image/*";
    }

    /**
     * Returns an ordered list of {@link Size}s, with the first being the smallest and the last being the largest
     * (full)
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
