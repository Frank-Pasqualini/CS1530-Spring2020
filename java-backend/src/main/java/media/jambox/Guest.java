package media.jambox;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Guest
    extends User
{
    private final transient ArrayList<String> voteList;

    /**
     * Initializes the guest with an ID and the Event it is a part of.
     *
     * @param id The Guest's ID.
     * @param event The Event the Guest is a part of.
     */
    public Guest(String id, Event event)
    {
        voteList = new ArrayList<>();

        this.id = id;
        this.event = event;
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
    public ArrayList<String> changeVote(String trackId, int value)
        throws InputMismatchException
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
                catch (InputMismatchException ignored)
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
                catch (InputMismatchException ignored)
                {
                    // Nothing bad should happen if a song is voted for that does not exist.
                }
                break;
            default:
                throw new InputMismatchException();

        }

        return voteList;
    }
}
