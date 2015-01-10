package com.todoist.mediaparser.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HttpUtils {
    public static String readFrom(HttpURLConnection connection) throws IOException {
        InputStream in = null;
        try {
            in = connection.getInputStream();
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[2048];
            for (int byteCount; (byteCount = in.read(buffer)) != -1; ) {
                builder.append(new String(buffer, 0, byteCount, "UTF-8"));
            }
            return builder.toString();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) { /* Ignore. */ }
        }
    }
}
