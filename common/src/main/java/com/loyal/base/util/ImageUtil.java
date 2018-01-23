package com.loyal.base.util;

import com.facebook.drawee.backends.pipeline.Fresco;

public class ImageUtil {

    public static void clearFrescoTemp() {
        Fresco.getImagePipeline().clearCaches();
        Fresco.getImagePipeline().clearDiskCaches();
        Fresco.getImagePipeline().clearMemoryCaches();
    }
}