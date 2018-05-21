package org.opencv.android;

import org.opencv.core.Mat;

/**
 * Created by Akira on 2/25/2018.
 */

public interface Filters {
    public abstract void apply(final Mat src, final Mat dst);
}

