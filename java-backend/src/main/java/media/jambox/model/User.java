package media.jambox.model;

import java.util.ArrayList;
import java.util.InputMismatchException;

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
     * @throws InputMismatchException Throws an exception if value is not 1, -1, or 0.
     */
    public ArrayList<ArrayList<String>> changeVote(String trackId, int value)
        throws InputMismatchException
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
                try
                {
                    event.getQueue().vote(trackId, -1);
                    downvoted.add(trackId);
                }
                catch (java.util.InputMismatchException ignored)
                {
                    // Nothing bad should happen if a song is voted for that does not exist.
                }
                break;
            case 0:
                break;
            case 1:
                try
                {
                    event.getQueue().vote(trackId, 1);
                    upvoted.add(trackId);
                }
                catch (java.util.InputMismatchException ignored)
                {
                    // Nothing bad should happen if a song is voted for that does not exist.
                }
                break;
            default:
                throw new java.util.InputMismatchException();

        }

        ArrayList<ArrayList<String>> voteList = new ArrayList<>();

        voteList.add(upvoted);
        voteList.add(downvoted);

        return voteList;
    }

    /**
     * Requests a song.
     *
     * @param trackId The ID of the track being requested.
     *
     * @return True if the song is successfully added, false otherwise.
     */
    public boolean requestTrack(String trackId)
    {
        try
        {
            event.getQueue().append(trackId);
            return true;
        }
        catch (java.util.InputMismatchException e)
        {
            return false;
        }
    }
}
