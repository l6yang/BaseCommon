package com.loyal.base.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {

    /**
     * 关闭流
     *
     * @param stream 可关闭的流
     */
    public static void closeStream(Closeable stream) {
        try {
            if (null != stream)
                stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
