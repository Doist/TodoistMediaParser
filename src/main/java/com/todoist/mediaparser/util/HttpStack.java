package com.todoist.mediaparser.util;

import java.io.IOException;
import java.util.Map;

/**
 * An HTTP stack abstraction.
 */
public interface HttpStack {
    /**
     * Performs an HTTP GET request.
     */
    public String getResponse(String url) throws IOException;

    /**
     * Performs an HTTP GET request but only fetches the headers.
     */
    public Map<String, String> getHeaders(String url) throws IOException;
}
