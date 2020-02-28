package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
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
    private final transient String id = "7GhIk7Il098yCjg4BQjzvb";
    private transient Track testTrack;

    @Before
    public void setUp()
        throws IOException, SpotifyWebApiException
    {
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
        throws IOException, SpotifyWebApiException
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
        assertEquals("Never Gonna Give You Up - Rick Astley", testTrack.toString());
    }

    @Test
    public void testEquals()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        Track testTrack2 = new Track(id, accessToken);
        assertEquals(testTrack, testTrack2);
    }

    @Test
    public void testHash()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        Track testTrack2 = new Track("0l2kEdf5XjlckyybbNjmYS", accessToken);
        assertNotEquals(testTrack.hashCode(), testTrack2.hashCode());
    }

    @Test
    public void testNullInputs()
        throws IOException, SpotifyWebApiException
    {
        try
        {
            Track testTrack1 = new Track(null, accessToken);
            assertNull(testTrack1);
        }
        catch (AssertionError e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            Track testTrack2 = new Track(id, null);
            assertNull(testTrack2);
        }
        catch (AssertionError e)
        {
            assertNull(e.getMessage());
        }
    }

    @Test
    public void testBadID()
        throws IOException, SpotifyWebApiException
    {
        try
        {
            Track testTrack1 = new Track("", accessToken);
            assertNull(testTrack1);
        }
        catch (AssertionError e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            Track testTrack2 = new Track("a", accessToken);
            assertNull(testTrack2);
        }
        catch (BadRequestException e)
        {
            assertEquals("invalid id", e.getMessage());
        }

        try
        {
            Track testTrack3 = new Track("aaaaaaaaaaaaaaaaaaaaaa", accessToken);
            assertNull(testTrack3);
        }
        catch (NotFoundException e)
        {
            assertEquals("non existing id", e.getMessage());
        }
    }

    @Test
    public void testBadAccessToken()
        throws IOException, SpotifyWebApiException
    {
        try
        {
            Track testTrack1 = new Track("6M14BiCN00nOsba4JaYsHW", "");
            assertNull(testTrack1);
        }
        catch (AssertionError e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            Track testTrack2 = new Track("3cfOd4CMv2snFaKAnMdnvK", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            assertNull(testTrack2);
        }
        catch (UnauthorizedException e)
        {
            assertEquals("Invalid access token", e.getMessage());
        }

        try
        {
            Track testTrack3 = new Track("24CXuh2WNpgeSYUOvz14jk", "BQCjm_vxykeaXCWOTOTFj2q1-fm7c1JtqtiOSrxRSfk19w7FoWI77Wh0W93JD50lYRIkoV8R5F-fY5kUdWuTVgPShQg40x_GMVDQMTDf1CMRBye-wcd3GkZbbAQzPGk3cLx_vbeguZxLT8U");
            assertNull(testTrack3);
        }
        catch (UnauthorizedException e)
        {
            assertEquals("The access token expired", e.getMessage());
        }
    }
}
