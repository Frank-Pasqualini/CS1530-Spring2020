package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.ForbiddenException;
import com.wrapper.spotify.exceptions.detailed.UnauthorizedException;
import com.wrapper.spotify.requests.data.playlists.RemoveTracksFromPlaylistRequest;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class PlaylistTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String accessTokenScoped = System.getenv("TEST_ACCESS_CODE_SCOPED");
    private final transient String id = "45McaMvSG3vovfffyxEHz8";
    private transient Playlist testPlaylist;

    @Before
    public void setUp()
    {

        testPlaylist = new Playlist(id);
    }

    @Test
    public void testGetId()
    {
        assertEquals(id, testPlaylist.getId());
    }

    @Test
    public void testAppend()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        final JsonArray tracks = new JsonParser().parse("[{\"uri\":\"spotify:track:7GhIk7Il098yCjg4BQjzvb\"}]").getAsJsonArray();
        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessTokenScoped).build();
        final RemoveTracksFromPlaylistRequest removeTracksFromPlaylistRequest = spotifyApi.removeTracksFromPlaylist(id, tracks).build();

        testPlaylist.append("7GhIk7Il098yCjg4BQjzvb", accessTokenScoped);

        removeTracksFromPlaylistRequest.execute();
    }

    @Test
    public void testNullInputs()
        throws IOException, SpotifyWebApiException
    {
        try
        {
            assertNull(testPlaylist.append(null, accessTokenScoped));
        }
        catch (BadRequestException e)
        {
            assertEquals("Invalid track uri: spotify:track:null", e.getMessage());
        }

        try
        {
            assertNull(testPlaylist.append("7GhIk7Il098yCjg4BQjzvb", null));
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
            assertNull(testPlaylist.append("", accessTokenScoped));
        }
        catch (BadRequestException e)
        {
            assertEquals("Invalid track uri: spotify:track:", e.getMessage());
        }

        try
        {
            assertNull(testPlaylist.append("a", accessTokenScoped));
        }
        catch (BadRequestException e)
        {
            assertEquals("Invalid track uri: spotify:track:a", e.getMessage());
        }

        try
        {
            assertNull(testPlaylist.append("aaaaaaaaaaaaaaaaaaaaaa", accessTokenScoped));
        }
        catch (BadRequestException e)
        {
            assertEquals("Payload contains a non-existing ID", e.getMessage());
        }
    }

    @Test
    public void testBadAccessToken()
        throws IOException, SpotifyWebApiException
    {
        try
        {
            assertNull(testPlaylist.append("6M14BiCN00nOsba4JaYsHW", ""));
        }
        catch (AssertionError e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            assertNull(testPlaylist.append("3cfOd4CMv2snFaKAnMdnvK", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        }
        catch (UnauthorizedException e)
        {
            assertEquals("Invalid access token", e.getMessage());
        }

        try
        {
            assertNull(testPlaylist.append("24CXuh2WNpgeSYUOvz14jk", "BQCjm_vxykeaXCWOTOTFj2q1-fm7c1JtqtiOSrxRSfk19w7FoWI77Wh0W93JD50lYRIkoV8R5F-fY5kUdWuTVgPShQg40x_GMVDQMTDf1CMRBye-wcd3GkZbbAQzPGk3cLx_vbeguZxLT8U"));
        }
        catch (UnauthorizedException e)
        {
            assertEquals("The access token expired", e.getMessage());
        }

        try
        {
            assertEquals("0l2kEdf5XjlckyybbNjmYS", testPlaylist.append("0l2kEdf5XjlckyybbNjmYS", accessToken));
        }
        catch (ForbiddenException e)
        {
            assertEquals("Insufficient client scope", e.getMessage());
        }
    }
}
