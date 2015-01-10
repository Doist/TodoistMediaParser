package com.todoist.mediaparser.util;

public class Size {
    public String key;
    public int smallestSide;

    public Size(String key, int smallestSide) {
        this.key = key;
        this.smallestSide = smallestSide;
    }

    /**
     * Returns the appropriate {@link Size} for {@code smallestSide}, the first {@code Side} equal to or larger. If
     * none
     * is found, returns null.
     */
    public static Size getSizeForSmallestSide(Size[] availableSizes, int smallestSide) {
        Size size = null;
        for (Size availableSize : availableSizes) {
            if (availableSize.smallestSide >= smallestSide) {
                size = availableSize;
                break;
            }
        }
        return size;
    }

    /**
     * Returns the best {@link Size} for {@code smallestSide}. If none is found, returns the largest available.
     */
    public static Size getBestSizeForSmallestSide(Size[] availableSizes, int smallestSide) {
        Size size = getSizeForSmallestSide(availableSizes, smallestSide);
        if (size == null) {
            size = availableSizes[availableSizes.length - 1];
        }
        return size;
    }
}
