package media.jambox.model;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.ArrayList;

public class User
{
    protected final transient String id;
    protected final transient Event event;
    protected final transient int eventCode;
    protected final transient ArrayList<String> upvoted;
    protected final transient ArrayList<String> downvoted;

    /**
     * Initializes the User with an ID and the Event it is a part of.
     *
     * @param id The User's ID.
     * @param event The Event the User is a part of.
     */
    public User(String id, Event event)
    {
        upvoted = new ArrayList<>();
        downvoted = new ArrayList<>();

        this.id = id;
        this.event = event;
        eventCode = event.getEventCode();
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        return getClass() == obj.getClass() && (id.equals(((User)obj).getId()) && eventCode == ((User)obj).getEventCode());
    }

    public String getId()
    {
        return id;
    }

    public int getEventCode()
    {
        return eventCode;
    }

    public ArrayList<String> getUpvoted()
    {
        return upvoted;
    }

    public ArrayList<String> getDownvoted()
    {
        return downvoted;
    }

    /**
     * Changes the vote values for a Track for this User.
     *
     * @param trackId The ID of the track that is being voted on.
     * @param value Either 1 to represent an upvote, -1 to represent a downvote, or 0 to represent a vote removal.
     *
     * @return The list of votes that the User has made.
     *
     * @throws IllegalArgumentException Throws an exception if value is not 1, -1, or 0.
     */
    public ArrayList<ArrayList<String>> changeVote(String trackId, int value)
        throws IllegalArgumentException
    {
        if (upvoted.contains(trackId))
        {
            upvoted.remove(trackId);
            event.getQueue().vote(trackId, -1);
        }

        if (downvoted.contains(trackId))
        {
            downvoted.remove(trackId);
            event.getQueue().vote(trackId, 1);
        }

        switch (value)
        {
            case -1:
                event.getQueue().vote(trackId, -1);
                downvoted.add(trackId);
                break;
            case 0:
                break;
            case 1:
                event.getQueue().vote(trackId, 1);
                upvoted.add(trackId);
                break;
            default:
                throw new IllegalArgumentException("Votes can only be 1 up, 1 down, or removed.");
        }

        ArrayList<ArrayList<String>> voteList = new ArrayList<>();

        voteList.add(upvoted);
        voteList.add(downvoted);

        return voteList;
    }

    public void requestTrack(String trackId)
        throws IOException, SpotifyWebApiException
    {
        event.getQueue().append(trackId);
    }

    public void disconnect()
    {
        event.removeUser(id);
    }
}
