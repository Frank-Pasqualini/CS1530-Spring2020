package media.jambox;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Guest
    implements User
{
    private String id;

    private ArrayList<String> voteList;

    public Guest(String id)
    {
        this.id = id;
        this.voteList = new ArrayList<>();
    }

    @Override
    public String getId()
    {
        return this.id;
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
        this.voteList.remove("+" + trackId);
        this.voteList.remove("-" + trackId);
        if (value == 1)
        {
            this.voteList.add("+" + trackId);
        }
        else if (value == -1)
        {
            this.voteList.add("-" + trackId);
        }
        else if (value != 0)
        {
            throw new InputMismatchException();
        }

        return this.voteList;
    }
}
