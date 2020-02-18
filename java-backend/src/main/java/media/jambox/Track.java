package media.jambox;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;

public class Track
{
    private transient String id;
    private transient String name;
    private transient int durationMS;
    private transient String albumName;
    private transient String[] albumImages;
    private transient String[] artistNames;
    private int score;

    /**
     * The initializer for a Track object.
     * @param id The Spotify ID for the Track.
     * @param accessToken The Host's Spotify Access Token.
     *
     * @throws java.io.IOException An I/O exception occurred while searching for the Track information on Spotify.
     * @throws com.wrapper.spotify.exceptions.SpotifyWebApiException An API exception occurred while searching for the Track information on Spotify.
     */
    public Track(String id, String accessToken) throws  java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final GetTrackRequest trackRequest = spotifyApi.getTrack(id).build();

        this.id = id;
        this.score = 0;

        final com.wrapper.spotify.model_objects.specification.Track trackInfo = trackRequest.execute();
        this.name = trackInfo.getName();
        this.durationMS = trackInfo.getDurationMs();
        this.albumName = trackInfo.getAlbum().getName();
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

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public int getDurationMS()
    {
        return this.durationMS;
    }

    public int getScore()
    {
        return this.score;
    }

    private void setScore(int s)
    {
        this.score = s;
    }

    public String getAlbumName()
    {
        return this.albumName;
    }

    public String[] getAlbumImages()
    {
        return this.albumImages;
    }

    public String[] getArtistNames()
    {
        return this.artistNames;
    }

    public void incrementScore()
    {
        setScore(this.score + 1);
    }

    public void decrementScore()
    {
        setScore(this.score - 1);
    }
}
