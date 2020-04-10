package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.UnauthorizedException;
import com.wrapper.spotify.requests.data.playlists.RemoveTracksFromPlaylistRequest;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class PlaylistTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String id = "45McaMvSG3vovfffyxEHz8";
    private final transient String neverGonnaGiveYouUp = "7GhIk7Il098yCjg4BQjzvb";
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

    /**
     * Removes a song from the playlist to ensure the same environment for each test.
     *
     * @param track The ID of the track to remove.
     * @param accessToken The access token to attempt to remove with.
     *
     * @throws IOException An I/O exception occurred while removing the Track.
     * @throws SpotifyWebApiException An API exception occurred while removing the Track.
     */
    public void removeFromPlaylist(String track, String accessToken)
        throws IOException, SpotifyWebApiException
    {
        final JsonArray tracks = new JsonParser().parse("[{\"uri\":\"spotify:track:" + track + "\"}]").getAsJsonArray();
        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final RemoveTracksFromPlaylistRequest removeTracksFromPlaylistRequest = spotifyApi.removeTracksFromPlaylist(id, tracks).build();
        removeTracksFromPlaylistRequest.execute();
    }

    @Test
    public void testAppend()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        assertEquals(neverGonnaGiveYouUp, testPlaylist.append(neverGonnaGiveYouUp, accessToken));
        removeFromPlaylist(neverGonnaGiveYouUp, accessToken);
    }

    @Test(expected = BadRequestException.class)
    public void testNullTrack()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append(null, accessToken);
        removeFromPlaylist(null, accessToken);
    }

    @Test(expected = AssertionError.class)
    public void testNullAccessToken()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append(neverGonnaGiveYouUp, null);
        removeFromPlaylist(neverGonnaGiveYouUp, null);
    }

    @Test(expected = BadRequestException.class)
    public void testEmptyID()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append("", accessToken);
        removeFromPlaylist("", accessToken);
    }

    @Test(expected = BadRequestException.class)
    public void testSmallID()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append("a", accessToken);
        removeFromPlaylist("a", accessToken);
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidID()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append("aaaaaaaaaaaaaaaaaaaaaa", accessToken);
        removeFromPlaylist("aaaaaaaaaaaaaaaaaaaaaa", accessToken);
    }

    @Test(expected = AssertionError.class)
    public void testEmptyAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String oceanMan = "6M14BiCN00nOsba4JaYsHW";
        testPlaylist.append(oceanMan, "");
        removeFromPlaylist(oceanMan, "");
    }

    @Test(expected = UnauthorizedException.class)
    public void testShortAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String pumpedUpKicks = "7w87IxuO7BDcJ3YUqCyMTT";
        testPlaylist.append(pumpedUpKicks, "a");
        removeFromPlaylist(pumpedUpKicks, "a");
    }

    @Test(expected = UnauthorizedException.class)
    public void testInvalidAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String allStar = "3cfOd4CMv2snFaKAnMdnvK";
        testPlaylist.append(allStar, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        removeFromPlaylist(allStar, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test(expected = UnauthorizedException.class)
    public void testExpiredAccessToken()
        throws IOException, SpotifyWebApiException
    {
        final String sandstorm = "24CXuh2WNpgeSYUOvz14jk";
        testPlaylist.append(sandstorm, "BQCjm_vxykeaXCWOTOTFj2q1-fm7c1JtqtiOSrxRSfk19w7FoWI77Wh0W93JD50lYRIkoV8R5F-fY5kUdWuTVgPShQg40x_GMVDQMTDf1CMRBye-wcd3GkZbbAQzPGk3cLx_vbeguZxLT8U");
        removeFromPlaylist(sandstorm, "BQCjm_vxykeaXCWOTOTFj2q1-fm7c1JtqtiOSrxRSfk19w7FoWI77Wh0W93JD50lYRIkoV8R5F-fY5kUdWuTVgPShQg40x_GMVDQMTDf1CMRBye-wcd3GkZbbAQzPGk3cLx_vbeguZxLT8U");
    }
}
