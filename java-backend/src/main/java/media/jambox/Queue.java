package media.jambox;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class Queue
{
    private final transient ArrayList<Track> trackList;

    Queue(String playlistId, String accessToken)
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        this.trackList = new ArrayList<>();

        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final GetPlaylistsTracksRequest getPlaylistsTracksRequest = spotifyApi.getPlaylistsTracks(playlistId).build();
        final PlaylistTrack[] playlistTracks = getPlaylistsTracksRequest.execute().getItems();
        for (final com.wrapper.spotify.model_objects.specification.PlaylistTrack playlistTrack : playlistTracks)
        {
            trackList.add(new Track(playlistTrack.getTrack().getId(), accessToken));
        }

    }

    /**
     * Add a track to the queue and reorder the queue. Does not add a Track if
     * one with the same ID is already in the Queue.
     *
     * @param trackId Track ID gained from user interaction.
     *
     * @return The updated queue.
     */
    public ArrayList<Track> append(String trackId, String accessToken)
    {
        for (int i = 0; i < trackList.size() - 1; i++)
        {
            if (trackList.get(i).getId().equals(trackId))
            {
                return trackList;
            }
        }

        try
        {
            trackList.add(new Track(trackId, accessToken));
        }
        catch (java.io.IOException | com.wrapper.spotify.exceptions.SpotifyWebApiException e)
        {
            throw new InputMismatchException();
        }

        this.sortAndDisplay();
        return trackList;
    }

    public ArrayList<Track> sortAndDisplay()
    {
        Collections.sort(trackList);
        return trackList;
    }

    public Track pop()
    {
        return trackList.remove(0);
    }

    /**
     * Removes a track from the queue via a string trackId.
     *
     * @param trackId String id to identify a track.
     *
     * @return The Track that was removed.
     *
     * @throws InputMismatchException Throws an exception when the Track to remove is not in the Queue.
     */
    public Track removeTrack(String trackId)
        throws InputMismatchException
    {
        for (int i = 0; i < trackList.size() - 1; i++)
        {
            if (trackList.get(i).getId().equals(trackId))
            {
                return trackList.remove(i);
            }
        }
        throw new InputMismatchException();
    }

    /**
     * Votes on a track in the Queue.
     *
     * @param trackId The ID of the track to vote on.
     * @param value 1 for an upvote, -1 for a downvote.
     * @throws InputMismatchException Thrown whenever the track is not in the queue or the value is not 1 or -1.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void vote(String trackId, int value)
        throws InputMismatchException
    {
        for (Track track : trackList)
        {
            if (track.getId().equals(trackId))
            {
                switch (value)
                {
                    case 1:
                        track.incrementScore();
                        return;
                    case -1:
                        track.decrementScore();
                        return;
                    default:
                        throw new InputMismatchException();
                }
            }
        }
        throw new InputMismatchException();
    }
}