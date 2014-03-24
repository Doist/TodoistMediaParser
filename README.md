# Media Parser #

Simple Java library to ease integration with media. Useful for obtaining direct links to media content, thumbnails, mime types, and other useful data.

## Usage ##

To get a media parser for an url (or `null` if there's none that supports it), call:

```
MediaParser mediaParser = MediaParser.getInstance(url);
```

To get a direct link to this content, use:

```
mediaParser.getContentUrl(); // Same as `getUrl()` when there's no direct link.
```

To get the type of the content `getContentUrl()` returns, use:

```
mediaParser.getContentMediaType(); // Type.IMAGE, Type.VIDEO, Type.AUDIO or Type.OTHER.
```

To get an image thumbnail of this content, equal or larger than `smallestSide`, use:

```
mediaParser.getThumbnailUrl(smallestSide); // Can be null.
```

Finally, to know if the thumbnail can be obtained immedately or if it requires additonal work (like http requests), use:

```
public boolean isThumbnailImmediate(int smallestSide);
```

## Supported services
Besides urls which point directly to image, video and audio files, these services are also supported:

- deviantart.com
- flickr.com
- hulu.com
- img.ly
- instagr.am
- justin.tv
- rdio.com
- screenr.com
- slideshare.com
- soundcloud.com
- spotify.com
- ted.com
- twitpic.com
- vimeo.com
- yfrog.com
- youtube.com