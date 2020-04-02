package media.jambox;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import java.io.IOException;
import java.util.Arrays;

public class Track
    implements Comparable<Track>
{
    private final transient String id;
    private transient String name;
    private transient int durationMS;
    private transient String albumName;
    private transient String[] albumImages;
    private transient String[] artistNames;
    private transient int score;

    /**
     * The initializer for a Track object. Requests info from the Spotify API
     * based on the ID supplied and fills in all of the variables with that
     * info. The score starts at 0.
     *
     * @param id The Spotify ID for the Track.
     * @param accessToken The Host's Spotify Access Token.
     *
     * @throws IOException An I/O exception occurred while searching for the rack information on Spotify.
     * @throws SpotifyWebApiException An API exception occurred while searching for the Track information on Spotify.
     */
    public Track(String id, String accessToken)
        throws IOException, SpotifyWebApiException
    {
        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final GetTrackRequest trackRequest = spotifyApi.getTrack(id).build();

        this.id = id;
        score = 0;

        final com.wrapper.spotify.model_objects.specification.Track trackInfo = trackRequest.execute();
        name = trackInfo.getName();
        durationMS = trackInfo.getDurationMs();
        albumName = trackInfo.getAlbum().getName();
        Image[] albumImages = trackInfo.getAlbum().getImages();
        this.albumImages = new String[albumImages.length];
        ArtistSimplified[] artistNames = trackInfo.getArtists();
        this.artistNames = new String[artistNames.length];

        for (int i = 0; i < albumImages.length; i++)
        {
            this.albumImages[i] = albumImages[i].getUrl();
        }

        for (int i = 0; i < artistNames.length; i++)
        {
            this.artistNames[i] = artistNames[i].getName();
        }
    }

    public String getName()
    {
        return name;
    }

    public int getDurationMS()
    {
        return durationMS;
    }

    public int getScore()
    {
        return score;
    }

    public String getAlbumName()
    {
        return albumName;
    }

    public String[] getAlbumImages()
    {
        return albumImages;
    }

    public String[] getArtistNames()
    {
        return artistNames;
    }

    public int incrementScore()
    {
        return ++score;
    }

    public int decrementScore()
    {
        return --score;
    }

    @Override
    public int compareTo(final Track other)
    {
        return Integer.compare(score, other.score);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        return getClass() == obj.getClass() && getId().equals(((Track)obj).getId());
    }

    public String getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        String artists = Arrays.toString(artistNames);
        return name + " - " + artists.substring(1, artists.length() - 1);
    }
}
