package com.todoist.mediaparser.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleHttpStack implements HttpStack {
    @Override
    public String getResponse(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
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
            if (in != null) {
                in.close();
            }
        }
    }

    @Override
    public Map<String, String> getHeaders(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        Map<String, List<String>> connectionHeaders = connection.getHeaderFields();
        Map<String, String> headers = new HashMap<String, String>(connectionHeaders.size());
        for (String key : connectionHeaders.keySet()) {
            List<String> values = connectionHeaders.get(key);
            headers.put(key, values.get(values.size() - 1)); // Use last entry.
        }
        return headers;
    }
}
