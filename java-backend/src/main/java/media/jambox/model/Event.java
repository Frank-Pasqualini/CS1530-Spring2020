package media.jambox.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Event
{

    private final transient ArrayList<User> users;
    private final int eventCode;
    private final transient Playlist eventPlaylist;
    private final transient Queue eventQueue;
    private transient Track nowPlaying;
    private transient Track upNext;
    private final transient String accessToken;

    Event(int eventCode, String playlistId, String accessToken, String hostId, Playlist playlistOverride, Queue queueOverride)
        throws IOException, IllegalArgumentException, InvalidKeyException, IndexOutOfBoundsException, SpotifyWebApiException
    {
        if (eventCode < 0 || eventCode > 9999)
        {
            throw new IllegalArgumentException("Event codes must be in the range 0000-9999.");
        }

        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        com.wrapper.spotify.model_objects.specification.User host = getCurrentUsersProfileRequest.execute();
        if (!host.getProduct().type.equals("premium"))
        {
            throw new InvalidKeyException("The host does not have Spotify Premium.");
        }

        if (playlistOverride == null)
        {
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
            throw new IndexOutOfBoundsException("The playlist submitted does not have at least 2 songs.");
        }

        this.accessToken = accessToken;
        this.eventCode = eventCode;

        users = new ArrayList<>();
        users.add(new Host(hostId, this));
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
     * @throws NoSuchElementException Throws an exception when the User ID is not a current user.
     */
    public User removeUser(String userId)
        throws NoSuchElementException
    {
        for (int i = 0; i < users.size(); i++)
        {
            if (users.get(i).getId().equals(userId))
            {
                return users.remove(i);
            }
        }

        throw new NoSuchElementException("A user with the ID " + userId + " does not exist.");
    }

    /**
     * Returns the user with the ID specified.
     *
     * @param userId The user ID to search for.
     *
     * @return The user with the matching ID
     *
     * @throws NoSuchElementException Throws an exception if the user searched for does not exist.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public User getUser(String userId)
        throws NoSuchElementException
    {
        for (User user : users)
        {
            if (user.getId().equals(userId))
            {
                return user;
            }
        }
        throw new NoSuchElementException("A user with the ID " + userId + " does not exist.");
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
     * @param accessToken The accessToken associated with the event.
     *
     * @throws InvalidKeyException The user entered an incorrect access token.
     */
    public void deleteEvent(String accessToken)
        throws InvalidKeyException
    {
        if (!accessToken.equals(this.accessToken))
        {
            throw new InvalidKeyException("You do not have permission to perform this action.");
        }

        JamBox.removeEvent(eventCode);
    }

    /**
     * Cycles to the next song.
     *
     * @throws InvalidKeyException If the provided access token does not match the one associated with the event.
     * @throws IndexOutOfBoundsException If there are no more songs in the Queue.
     * @throws IOException An I/O exception occurred while searching for the Track information on Spotify.
     * @throws SpotifyWebApiException An API exception occurred while searching for the Track information on Spotify.
     */
    public void cycle(String accessToken)
        throws InvalidKeyException, IndexOutOfBoundsException, IOException, SpotifyWebApiException
    {
        nowPlaying = upNext;
        if (!accessToken.equals(this.accessToken))
        {
            throw new InvalidKeyException("You do not have permission to perform this action.");
        }

        try
        {
            upNext = eventQueue.pop();
            eventPlaylist.append(upNext.getId());
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new IndexOutOfBoundsException("There are no songs left in the queue");
        }
    }
}
