package com.todoist.mediaparser;

import com.todoist.mediaparser.util.HttpUtils;
import com.todoist.mediaparser.util.MediaType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class YoutubeParser extends AbsMediaParserWithId {
    private static final String THUMBNAIL_REGEXP = "\"thumbnail_url\"\\s*:\\s*\"([^\"]+)\"";
    private static final String TEMPLATE = "http://www.youtube.com/oembed?url=%s&format=json";

    private static Pattern sIdPattern;
    private static Pattern sThumbnailUrlPattern;

    YoutubeParser(String url) {
        super(url);
    }

    @Override
    protected boolean matches() {
        return (mUrl.contains("youtube.com/") || mUrl.contains("youtu.be/")) && super.matches();
    }

    @Override
    public String createThumbnailUrl(int smallestSide) {
        try {
            URL url = new URL(String.format(TEMPLATE, mUrl));
            String response = HttpUtils.readFrom((HttpURLConnection)url.openConnection());

            if(sThumbnailUrlPattern == null)
                sThumbnailUrlPattern = Pattern.compile(THUMBNAIL_REGEXP);

            Matcher matcher = sThumbnailUrlPattern.matcher(response);
            if(matcher.find())
                return matcher.group(1).replaceAll("\\\\/", "/");
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String createContentUrl() {
        return mUrl;
    }

    @Override
    public MediaType createContentMediaType() {
        return MediaType.VIDEO;
    }

    @Override
    protected Pattern getIdPattern() {
        if(sIdPattern == null) {
            sIdPattern = Pattern.compile(
                    "(https?://(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([\\w-]+)/?)",
                    Pattern.CASE_INSENSITIVE
            );
        }
        return sIdPattern;
    }
}
