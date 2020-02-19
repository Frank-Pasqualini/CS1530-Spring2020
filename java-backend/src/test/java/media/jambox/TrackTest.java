package media.jambox;

import static org.junit.Assert.assertEquals;

public class TrackTest
{
    @org.junit.Test
    public void testTrack()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        final String accessToken = "BQCjm_vxykeaXCWOTOTFj2q1-fm7c1JtqtiOSrxRSfk19w7FoWI77Wh0W93JD50lYRIkoV8R5F-fY5kUdWuTVgPShQg40x_GMVDQMTDf1CMRBye-wcd3GkZbbAQzPGk3cLx_vbeguZxLT8U";
        final String id = "7GhIk7Il098yCjg4BQjzvb";

        Track testTrack = new Track(id, accessToken);

        assertEquals(id, testTrack.getId());
        assertEquals("Never Gonna Give You Up", testTrack.getName());
        assertEquals(212826, testTrack.getDurationMS());
        assertEquals(0, testTrack.getScore());
        testTrack.incrementScore();
        assertEquals(1, testTrack.getScore());
        testTrack.decrementScore();
        assertEquals(0, testTrack.getScore());
        assertEquals("Whenever You Need Somebody", testTrack.getAlbumName());
        assertEquals(3, testTrack.getAlbumImages().length);
        assertEquals("https://i.scdn.co/image/ab67616d0000b273237665d08de01907e82a7d8a", testTrack.getAlbumImages()[0]);
        assertEquals(1, testTrack.getArtistNames().length);
        assertEquals("Rick Astley", testTrack.getArtistNames()[0]);
    }
}
