package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.exceptions.detailed.UnauthorizedException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class TrackTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String neverGonnaGiveYouUp = "7GhIk7Il098yCjg4BQjzvb";
    private final transient String despacito = "6rPO02ozF3bM7NnOV4h6s2";
    private transient Track testTrack;

    @Before
    public void setUp()
        throws IOException, SpotifyWebApiException
    {
        testTrack = new Track(neverGonnaGiveYouUp, accessToken);
    }

    @Test
    public void testGetId()
    {
        assertEquals(neverGonnaGiveYouUp, testTrack.getId());
    }

    @Test
    public void testGetName()
    {
        assertEquals("Never Gonna Give You Up", testTrack.getName());
    }

    @Test
    public void testGetDurationMS()
    {
        assertEquals(212826, testTrack.getDurationMS());
    }

    @Test
    public void testGetScoreInitial()
    {
        assertEquals(0, testTrack.getScore());
    }

    @Test
    public void testGetAlbumName()
    {
        assertEquals("Whenever You Need Somebody", testTrack.getAlbumName());
    }

    @Test
    public void testGetAlbumImages()
    {
        assertEquals(3, testTrack.getAlbumImages().length);
        assertEquals("https://i.scdn.co/image/ab67616d0000b273237665d08de01907e82a7d8a", testTrack.getAlbumImages()[0]);
    }

    @Test
    public void testGetArtistNames()
    {
        assertEquals(1, testTrack.getArtistNames().length);
        assertEquals("Rick Astley", testTrack.getArtistNames()[0]);
    }

    @Test
    public void testIncrementScore()
    {
        assertEquals(1, testTrack.incrementScore());
    }

    @Test
    public void testDecrementScore()
    {
        assertEquals(-1, testTrack.decrementScore());
    }

    @Test
    public void testCompare()
        throws IOException, SpotifyWebApiException
    {
        Track testTrack2 = new Track(despacito, accessToken);

        testTrack.incrementScore();
        testTrack2.decrementScore();

        assertTrue(testTrack.compareTo(testTrack2) > 0);
    }

    @Test
    public void testToString()
    {
        assertEquals("Never Gonna Give You Up - Rick Astley", testTrack.toString());
    }

    @Test
    public void testEquals()
        throws IOException, SpotifyWebApiException
    {
        Track testTrack2 = new Track(neverGonnaGiveYouUp, accessToken);
        assertEquals(testTrack, testTrack2);
    }

    @Test
    public void testNotEquals()
        throws IOException, SpotifyWebApiException
    {
        Track testTrack3 = new Track(despacito, accessToken);
        assertNotEquals(testTrack, testTrack3);
    }

    @Test
    public void testNotEqualsWrongObject()
    {
        assertNotEquals(testTrack, "test");
    }

    @Test
    public void testHash()
        throws IOException, SpotifyWebApiException
    {
        final String roundabout = "0l2kEdf5XjlckyybbNjmYS";
        Track testTrack2 = new Track(roundabout, accessToken);
        assertNotEquals(testTrack.hashCode(), testTrack2.hashCode());
    }

    @Test(expected = AssertionError.class)
    public void testNullInputId()
        throws IOException, SpotifyWebApiException
    {

        new Track(null, accessToken);
    }

    @Test(expected = AssertionError.class)
    public void testNullAccessToken()
        throws IOException, SpotifyWebApiException
    {
        new Track(neverGonnaGiveYouUp, null);
    }

    @Test(expected = AssertionError.class)
    public void testEmptyID()
        throws IOException, SpotifyWebApiException
    {
        new Track("", accessToken);
    }

    @Test(expected = BadRequestException.class)
    public void testSmallID()
        throws IOException, SpotifyWebApiException
    {
        new Track("a", accessToken);
    }

    @Test(expected = NotFoundException.class)
    public void testInvalidID()
        throws IOException, SpotifyWebApiException
    {
        new Track("aaaaaaaaaaaaaaaaaaaaaa", accessToken);
    }

    @Test(expected = AssertionError.class)
    public void testEmptyAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String oceanMan = "6M14BiCN00nOsba4JaYsHW";
        new Track(oceanMan, "");
    }

    @Test(expected = UnauthorizedException.class)
    public void testShortAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String pumpedUpKicks = "7w87IxuO7BDcJ3YUqCyMTT";
        new Track(pumpedUpKicks, "a");
    }

    @Test(expected = UnauthorizedException.class)
    public void testInvalidAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String allStar = "3cfOd4CMv2snFaKAnMdnvK";
        new Track(allStar, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test(expected = UnauthorizedException.class)
    public void testExpiredAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String sandstorm = "24CXuh2WNpgeSYUOvz14jk";
        new Track(sandstorm, "BQCjm_vxykeaXCWOTOTFj2q1-fm7c1JtqtiOSrxRSfk19w7FoWI77Wh0W93JD50lYRIkoV8R5F-fY5kUdWuTVgPShQg40x_GMVDQMTDf1CMRBye-wcd3GkZbbAQzPGk3cLx_vbeguZxLT8U");
    }
}
