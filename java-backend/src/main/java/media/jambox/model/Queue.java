package media.jambox.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Queue
{
    private final transient ArrayList<Track> trackList;
    private final transient String accessToken;

    Queue(String playlistId, String accessToken)
        throws IOException, SpotifyWebApiException
    {
        trackList = new ArrayList<>();

        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final GetPlaylistsTracksRequest getPlaylistsTracksRequest = spotifyApi.getPlaylistsTracks(playlistId).build();
        final PlaylistTrack[] playlistTracks = getPlaylistsTracksRequest.execute().getItems();
        for (final PlaylistTrack playlistTrack : playlistTracks)
        {
            trackList.add(new Track(playlistTrack.getTrack().getId(), accessToken));
        }

        this.accessToken = accessToken;
    }

    /**
     * Add a track to the queue and reorder the queue. Does not add a Track if
     * one with the same ID is already in the Queue.
     *
     * @param trackId Track ID gained from user interaction.
     *
     * @return The position of the track in the queue
     *
     * @throws IOException An I/O exception occurred while searching for the rack information on Spotify.
     * @throws SpotifyWebApiException An API exception occurred while searching for the Track information on Spotify.
     */
    public int append(String trackId)
        throws IOException, SpotifyWebApiException
    {
        for (int i = 0; i < trackList.size(); i++)
        {
            if (trackList.get(i).getId().equals(trackId))
            {
                return i;
            }
        }

        Track addedTrack;
        addedTrack = new Track(trackId, accessToken);
        trackList.add(trackList.size(), addedTrack);

        getTrackList();
        return trackList.indexOf(addedTrack);
    }

    public ArrayList<Track> getTrackList()
    {
        trackList.sort(Collections.reverseOrder());
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
     * @throws InvalidKeyException Throws an exception when the access token associated with the Queue is not provided.
     * @throws NoSuchElementException Throws an exception when the Track to remove is not in the Queue.
     */
    public Track removeTrack(String trackId, String accessToken)
        throws InvalidKeyException, NoSuchElementException
    {
        if (!accessToken.equals(this.accessToken))
        {
            throw new InvalidKeyException("You do not have permission to perform this action.");
        }

        for (int i = 0; i < trackList.size(); i++)
        {
            if (trackList.get(i).getId().equals(trackId))
            {
                return trackList.remove(i);
            }
        }
        throw new NoSuchElementException("The track " + trackId + " is not in the Queue.");
    }

    /**
     * Checks to see if the Queue is empty.
     *
     * @return boolean true if empty, false if not
     */
    public boolean isEmpty()
    {
        return trackList.size() == 0;
    }

    /**
     * Votes on a track in the Queue.
     *
     * @param trackId The ID of the track to vote on.
     * @param value 1 for an upvote, -1 for a downvote.
     *
     * @return The sorted vote queue;
     *
     * @throws IllegalArgumentException Thrown whenever the track is not in the queue or the value is not 1 or -1.
     * @throws NoSuchElementException When the requested track is not in the queue.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public ArrayList<Track> vote(String trackId, int value)
        throws IllegalArgumentException, NoSuchElementException
    {
        for (Track track : trackList)
        {
            if (track.getId().equals(trackId))
            {
                switch (value)
                {
                    case -1:
                        track.decrementScore();
                        return getTrackList();
                    case 1:
                        track.incrementScore();
                        return getTrackList();
                    default:
                        throw new IllegalArgumentException("A Track's score can only be incremented or decremented.");
                }
            }
        }
        throw new NoSuchElementException("The track " + trackId + " is not in the Queue.");
    }
}
