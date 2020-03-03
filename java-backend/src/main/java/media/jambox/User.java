package media.jambox;

public abstract class User
{
    protected transient String id;
    protected Event event;

    public String getId()
    {
        return id;
    }
}
