package media.jambox;

import java.util.ArrayList;

public class Queue
{
    private ArrayList<Track> trackList; // ArrayList

    Queue()
    {
        this.trackList = new ArrayList<Track>();
    }

    public ArrayList<Track> sortAndDisplay()
    {
        return trackList;
    }

    public Track pop()
    {
        return trackList.remove(0);
    }

    public ArrayList<Track> append(Track track)
    {
        trackList.add(track);
        return trackList;
    }

    public String remove(String trackId)
    {
        trackList.remove(trackId);
        return trackId;
    }
}