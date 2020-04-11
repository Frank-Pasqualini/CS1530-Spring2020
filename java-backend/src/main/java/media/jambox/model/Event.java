package media.jambox.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Event
{

    private final transient ArrayList<User> users;
    private final int eventCode;
    private final transient Queue eventQueue;
    private final transient Track nowPlaying;
    private final transient Track upNext;
    private final transient String accessToken;

    Event(int eventCode, String playlistId, String accessToken, String hostId, Playlist playlistOverride, Queue queueOverride)
        throws IOException, InputMismatchException, SpotifyWebApiException
    {
        users = new ArrayList<>();
        users.add(new Host(hostId, this));

        this.accessToken = accessToken;

        Playlist eventPlaylist;
        if (playlistOverride == null)
        {
            final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            final LocalDateTime now = LocalDateTime.now();
            final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(hostId, "JamBox: " + dtf.format(now)).build();
            final com.wrapper.spotify.model_objects.specification.Playlist playlist = createPlaylistRequest.execute();
            eventPlaylist = new Playlist(playlist.getId(), accessToken);
        }
        else
        {
            eventPlaylist = playlistOverride;
        }

        if (queueOverride == null)
        {
            eventQueue = new Queue(playlistId, accessToken);
        }
        else
        {
            eventQueue = queueOverride;
        }
        try
        {
            nowPlaying = eventQueue.pop();
            upNext = eventQueue.pop();
            eventPlaylist.append(nowPlaying.getId());
            eventPlaylist.append(upNext.getId());
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new InputMismatchException();
        }

        this.eventCode = eventCode;
    }

    /**
     * Adds a new user to the Users list.
     *
     * @param userId The ID for the user.
     *
     * @return True if its a new user, false if its a returning user.
     */
    public boolean addUser(String userId)
    {
        User newUser = new User(userId, this);
        if (users.contains(newUser))
        {
            return false;
        }
        users.add(newUser);
        return true;
    }

    /**
     * Removes a User via a userID string input from the user ArrayList.
     *
     * @param userId A string input of a userId.
     *
     * @return The User that was removed.
     *
     * @throws InputMismatchException Throws an exception when the User ID is not a current user.
     */
    public User removeUser(String userId)
        throws InputMismatchException
    {
        for (int i = 0; i < users.size(); i++)
        {
            if (users.get(i).getId().equals(userId))
            {
                return users.remove(i);
            }
        }

        throw new InputMismatchException();
    }

    /**
     * Returns the user with the ID specified.
     *
     * @param userId The user ID to search for.
     *
     * @return The user with the matching ID
     *
     * @throws InputMismatchException Throws an exception if the user searched for does not exist.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public User getUser(String userId)
        throws InputMismatchException
    {
        for (User user : users)
        {
            if (user.getId().equals(userId))
            {
                return user;
            }
        }
        throw new InputMismatchException();
    }

    public Queue getQueue()
    {
        return eventQueue;
    }

    public Track getNowPlaying()
    {
        return nowPlaying;
    }

    public Track getUpNext()
    {
        return upNext;
    }

    public int getEventCode()
    {
        return eventCode;
    }

    /**
     * Deletes the event if the host enters the correct accessToken.
     *
     * @param accessToken the accessToken associated with the event.
     */
    public void deleteEvent(String accessToken)
    {
        if (!accessToken.equals(this.accessToken))
        {
            throw new InputMismatchException();
        }

        JamBox.removeEvent(eventCode);
    }
}
