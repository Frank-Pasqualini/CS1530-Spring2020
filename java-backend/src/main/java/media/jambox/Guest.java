package media.jambox;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.UUID;

public class Guest implements User
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

    public ArrayList<String> changeVote(String track, int value) throws InputMismatchException
    {
        this.voteList.remove("+" + track);
        this.voteList.remove("-" + track);
        if (value == 1)
        {
            this.voteList.add("+" + track);
        }
        else if (value == -1)
        {
            this.voteList.add("-" + track);
        }
        else
        {
            throw new InputMismatchException();
        }

        return this.voteList;
    }
}
