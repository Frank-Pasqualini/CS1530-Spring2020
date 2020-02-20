package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class TrackTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private transient Track testTrack;

    @Before
    public void setUp()
        throws IOException, SpotifyWebApiException
    {
        final String id = "7GhIk7Il098yCjg4BQjzvb";
        testTrack = new Track(id, accessToken);
    }

    @Test
    public void testTrackInfo()
    {
        assertEquals("7GhIk7Il098yCjg4BQjzvb", testTrack.getId());
        assertEquals("Never Gonna Give You Up", testTrack.getName());
        assertEquals(212826, testTrack.getDurationMS());
        assertEquals(0, testTrack.getScore());
        assertEquals("Whenever You Need Somebody", testTrack.getAlbumName());
        assertEquals(3, testTrack.getAlbumImages().length);
        assertEquals("https://i.scdn.co/image/ab67616d0000b273237665d08de01907e82a7d8a", testTrack.getAlbumImages()[0]);
        assertEquals(1, testTrack.getArtistNames().length);
        assertEquals("Rick Astley", testTrack.getArtistNames()[0]);
    }

    @Test
    public void testCompare()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        final String id2 = "6rPO02ozF3bM7NnOV4h6s2";

        Track testTrack2 = new Track(id2, accessToken);

        testTrack.incrementScore();
        testTrack2.decrementScore();

        assertTrue(testTrack.compareTo(testTrack2) > 0);
    }

    @Test
    public void testString()
    {
        assertEquals("Never Gonna Give You Up - [Rick Astley]", testTrack.toString());
    }
}
