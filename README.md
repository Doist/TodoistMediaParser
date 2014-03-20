# Media Parser #

Simple Java library for obtaining thumbnails and some simple data for media services.

## Usage ##

To get a media parser for an url (or `null` if there's none that supports it), call:

```
MediaParser mediaParser = MediaParser.getInstance(url);
```

To get the type of content `getUrl()` returns, use:

```
mediaParser.getType(); // Type.IMAGE, Type.VIDEO, Type.AUDIO or Type.EXTERNAL.
```

To get a thumbnail of this content, equal or larger than `smallestSide` use:

```
mediaParser.getThumbnailUrl(smallestSide);
```

To get a direct link to this content, use:

```
mediaParser.getUrl();
```

## Supported services
Besides urls which point directly to image, video and audio files, these services are also supported:

- img.ly
- instagr.am
- twitpic.com
- yfrog.com
- youtube.com
- vimeo.com