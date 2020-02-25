package media.jambox;

public class Host
    implements User
{
    private final String id;

    public Host(String id)
    {
        this.id = id;
    }

    @Override
    public String getId()
    {
        return this.id;
    }
}
