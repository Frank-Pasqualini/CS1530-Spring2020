package media.jambox.model;

import static com.google.gson.JsonParser.parseString;
import static org.junit.Assert.assertEquals;

import com.google.gson.JsonArray;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
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
        testPlaylist = new Playlist(id, accessToken);
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
     *
     * @throws IOException An I/O exception occurred while removing the Track.
     * @throws SpotifyWebApiException An API exception occurred while removing the Track.
     */
    public void removeFromPlaylist(String track)
        throws IOException, SpotifyWebApiException
    {
        final JsonArray tracks = parseString("[{\"uri\":\"spotify:track:" + track + "\"}]").getAsJsonArray();
        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final RemoveTracksFromPlaylistRequest removeTracksFromPlaylistRequest = spotifyApi.removeTracksFromPlaylist(id, tracks).build();
        removeTracksFromPlaylistRequest.execute();
    }

    @Test
    public void testAppend()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        assertEquals(neverGonnaGiveYouUp, testPlaylist.append(neverGonnaGiveYouUp));
        removeFromPlaylist(neverGonnaGiveYouUp);
    }

    @Test(expected = BadRequestException.class)
    public void testNullTrack()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append(null);
        removeFromPlaylist(null);
    }

    @Test(expected = BadRequestException.class)
    public void testEmptyID()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append("");
        removeFromPlaylist("");
    }

    @Test(expected = BadRequestException.class)
    public void testSmallID()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append("a");
        removeFromPlaylist("a");
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidID()
        throws IOException, SpotifyWebApiException
    {
        testPlaylist.append("aaaaaaaaaaaaaaaaaaaaaa");
        removeFromPlaylist("aaaaaaaaaaaaaaaaaaaaaa");
    }
}
