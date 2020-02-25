package media.jambox;

import java.util.ArrayList;

public class Event
{

    private ArrayList<User> users;
    private Playlist eventPlaylist;
    private Queue eventQueue;
    private Track nowPlaying;
    private int eventCode;

    Event(int eventCode, String playlistId)
    {
        this.users = new ArrayList<User>();
        this.eventPlaylist = new Playlist(playlistId);
        this.eventQueue = new Queue();
        this.nowPlaying = eventQueue.pop();
        this.eventCode = eventCode;
    }

    public ArrayList<User> addUser(String userId)
    {
        users.add(new Guest(userId));
        return users;
    }

    public String removeUser(String userId)
    {
        for (int i = 0; i < users.size() - 1; i++)
        {
            if (users.get(i).getId() == userId)
            {
                users.remove(i);
                return userId;
            }
        }
        return "[ERROR] could not find user to removed";
    }

    public Playlist getPlaylist()
    {
        return eventPlaylist;
    }

    public Queue getQueue()
    {
        return eventQueue;
    }

    public Track getNowPlaying()
    {
        return nowPlaying;
    }

    public Track setNowPlaying(Track track)
    {
        nowPlaying = track;
        return nowPlaying;
    }

    public int getEventCode()
    {
        return eventCode;
    }
}
