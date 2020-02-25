package media.jambox;

import java.util.ArrayList;
import java.util.Collections;

public class Queue
{
    private ArrayList<Track> trackList; // ArrayList

    Queue()
    {
        this.trackList = new ArrayList<Track>();
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
     * add a track to the queue and reorder the queue.
     * @param track track gained from user interaction
     * @return updated queue
     */
    public ArrayList<Track> append(Track track)
    {
        trackList.add(track);
        trackList = sortAndDisplay();
        return trackList;
    }

    /**
     * Removes a track from the queue via a string trackId.
     * @param trackId String id to identify a track
     * @return
     */
    public String removeTrack(String trackId)
    {
        for (int i = 0; i < trackList.size() - 1; i++)
        {
            if (trackList.get(i).getId() == trackId)
            {
                trackList.remove(i);
                return trackId;
            }
        }
        return "[ERROR] could not find track to remove";
    }
}