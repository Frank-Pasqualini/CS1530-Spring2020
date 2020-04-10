package media.jambox;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class User
{
    protected final transient String id;
    protected final transient Event event;

    protected final transient ArrayList<String> voteList;

    /**
     * Initializes the User with an ID and the Event it is a part of.
     *
     * @param id The User's ID.
     * @param event The Event the User is a part of.
     */
    public User(String id, Event event)
    {
        voteList = new java.util.ArrayList<>();

        this.id = id;
        this.event = event;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        return getClass() == obj.getClass() && getId().equals(((User)obj).getId());
    }

    public String getId()
    {
        return id;
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
    public java.util.ArrayList<String> changeVote(String trackId, int value)
        throws java.util.InputMismatchException
    {
        if (voteList.contains("+" + trackId))
        {
            voteList.remove("+" + trackId);
            event.getQueue().vote(trackId, -1);
        }
        if (voteList.contains("-" + trackId))
        {
            voteList.remove("-" + trackId);
            event.getQueue().vote(trackId, 1);
        }

        switch (value)
        {
            case -1:
                try
                {
                    event.getQueue().vote(trackId, -1);
                    voteList.add("-" + trackId);
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
                    voteList.add("+" + trackId);
                }
                catch (java.util.InputMismatchException ignored)
                {
                    // Nothing bad should happen if a song is voted for that does not exist.
                }
                break;
            default:
                throw new java.util.InputMismatchException();

        }

        return voteList;
    }

    /**
     * Requests a song.
     *
     * @param trackId The ID of the track being requested.
     *
     * @return -1 if the song does not exist, otherwise the position of the song in the Queue.
     */
    public int requestTrack(String trackId)
    {
        try
        {
            return event.getQueue().append(trackId);
        }
        catch (java.util.InputMismatchException e)
        {
            return -1;
        }
    }
}
