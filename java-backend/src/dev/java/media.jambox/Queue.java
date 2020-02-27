package media.jambox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class Queue
{
    private final transient ArrayList<Track> trackList;

    Queue(String playlistId)
    {
        this.trackList = new ArrayList<>();
        //TODO import playlist
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
     * Add a track to the queue and reorder the queue. Does not add a Track if
     * one with the same ID is already in the Queue.
     *
     * @param track Track gained from user interaction.
     *
     * @return The updated queue.
     */
    public ArrayList<Track> append(Track track)
    {
        for (int i = 0; i < trackList.size() - 1; i++)
        {
            if (trackList.get(i).equals(track))
            {
                return trackList;
            }
        }

        trackList.add(track);
        this.sortAndDisplay();
        return trackList;
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

    public void vote(String trackId, int value)
    {
        //TODO
    }
}