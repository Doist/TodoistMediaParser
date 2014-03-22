# Media Parser #

Simple Java library to ease integration with media. Useful for obtaining direct links to media content, thumbnails, mime types, and other useful data.

## Usage ##

To get a media parser for an url (or `null` if there's none that supports it), call:

```
MediaParser mediaParser = MediaParser.getInstance(url);
```

To get a direct link to this content, use:

```
mediaParser.getContentUrl();
```

To get an image thumbnail of this content, equal or larger than `smallestSide`, use:

```
mediaParser.getThumbnailUrl(smallestSide); // Can be null.
```

To get the type of the content `getUrl()` returns, use:

```
mediaParser.getContentMediaType(); // Type.IMAGE, Type.VIDEO, Type.AUDIO or Type.EXTERNAL.
```

To get the mime type of the content `getUrl()` returns, use:

```
mediaParser.getContentMimeType(); // Checks extension. If not certain, peeks content.
```

To get the mime type of the image thumbnail returned by `getThumbnailUrl(smallestSide)`, use:

```
mediaParser.getThumbnailMimeType(smallestSide);
```

## Supported services
Besides urls which point directly to image, video and audio files, these services are also supported:

- img.ly
- instagr.am
- twitpic.com
- yfrog.com
- youtube.com
- vimeo.com