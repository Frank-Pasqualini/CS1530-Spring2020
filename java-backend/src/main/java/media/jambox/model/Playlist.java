package media.jambox.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import java.io.IOException;

public class Playlist
{
    private final String id;
    private final transient String accessToken;

    public Playlist(String id, String accessToken)
    {
        this.id = id;
        this.accessToken = accessToken;
    }

    public String getId()
    {
        return this.id;
    }

    /**
     * Appends a Track to this playlist.
     *
     * @param trackId The id of the Track to append.
     *
     * @throws IOException An I/O exception occurred while searching for the Track information on Spotify.
     * @throws SpotifyWebApiException An API exception occurred while searching for the Track information on Spotify.
     */
    public String append(String trackId)
        throws IOException, SpotifyWebApiException
    {
        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final AddTracksToPlaylistRequest addTracksToPlaylistRequest = spotifyApi.addTracksToPlaylist(this.id, new String[] {"spotify:track:" + trackId}).build();
        addTracksToPlaylistRequest.execute();
        return trackId;
    }
}
