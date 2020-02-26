package media.jambox;

public abstract class User
{
    protected transient String id;

    protected Event event;

    public String getId()
    {
        return this.id;
    }

    public boolean equals(final Object obj)
    {
        return getClass() == obj.getClass() && this.getId().equals(((User)obj).getId());
    }
}
