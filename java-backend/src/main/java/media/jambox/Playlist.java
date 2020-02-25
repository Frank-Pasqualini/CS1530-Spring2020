package media.jambox;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import java.io.IOException;

public class Playlist
{
    private String id;

    public Playlist(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return this.id;
    }

    /**
     * Appends a Track to this playlist.
     *
     * @param trackId The id of the Track to append.
     * @param accessToken The Host's Spotify Access Token.
     *
     * @return The result of the attempted addition to the playlist.
     *
     * @throws IOException An I/O exception occurred while searching for the rack information on Spotify.
     * @throws SpotifyWebApiException An API exception occurred while searching for the Track information on Spotify.
     */
    public String append(String trackId, String accessToken)
        throws IOException, SpotifyWebApiException
    {
        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final AddTracksToPlaylistRequest addTracksToPlaylistRequest = spotifyApi.addTracksToPlaylist(this.id, new String[] {"spotify:track:" + trackId}).build();
        final SnapshotResult snapshotResult = addTracksToPlaylistRequest.execute();
        return snapshotResult.toString();
    }
}
