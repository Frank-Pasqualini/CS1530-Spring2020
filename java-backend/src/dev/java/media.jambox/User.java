package media.jambox;

public abstract class User
{
    protected transient String id;
    protected Event event;

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    public boolean equals(final Object obj)
    {
        return getClass() == obj.getClass() && this.getId().equals(((User)obj).getId());
    }

    public String getId()
    {
        return this.id;
    }
}
