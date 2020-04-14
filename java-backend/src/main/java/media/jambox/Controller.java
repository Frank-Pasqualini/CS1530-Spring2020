package media.jambox;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;
import media.jambox.model.Event;
import media.jambox.model.Host;
import media.jambox.model.JamBox;
import media.jambox.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Controller
{
    /**
     * API call to create a new event.
     *
     * @param playlistId The ID of the playlist that the event starts with.
     * @param accessToken The access token needed to create Spotify API requests.
     * @param hostId The ID of the Host on Spotify.
     *
     * @return The event code, -1 if the event setup failed.
     *
     * @throws InvalidKeyException
     * @throws IOException
     * @throws SpotifyWebApiException
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/add_event")
    public int newEvent(@RequestParam("playlistId") String playlistId, @RequestParam("accessToken") String accessToken, @RequestParam("hostId") String hostId)
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        return JamBox.addEvent(playlistId, accessToken, hostId, -1, null);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/event")
    public Event event(@RequestParam("eventCode") int eventCode)
    {
        return JamBox.getEvent(eventCode);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/add_user")
    public void addUser(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        JamBox.getEvent(eventCode).addUser(userId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/user")
    public User user(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        return JamBox.getEvent(eventCode).getUser(userId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/upvote")
    public void upvote(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        JamBox.getEvent(eventCode).getUser(userId).changeVote(trackId, 1);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/downvote")
    public void downvote(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        JamBox.getEvent(eventCode).getUser(userId).changeVote(trackId, -1);
    }

    /**
     * API call to request that a song is added to the Queue.
     *
     * @param eventCode The code of the Event.
     * @param userId The ID of the User making the request.
     * @param trackId The ID of the Track being requested.
     *
     * @return True if the request is successful, false otherwise.
     *
     * @throws InvalidKeyException
     * @throws SpotifyWebApiException
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/request")
    public void request(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
        throws IOException, SpotifyWebApiException
    {
        JamBox.getEvent(eventCode).getUser(userId).requestTrack(trackId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/disconnect")
    public void disconnect(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        JamBox.getEvent(eventCode).getUser(userId).disconnect();
    }

    /**
     * The API call for ending an event.
     *
     * @param eventCode The code of the Event to end.
     * @param hostId The Host of the Event's ID.
     * @param accessToken The access token associated with the Event.
     *
     * @return True if the event was successfully ended, false otherwise.
     *
     * @throws InvalidKeyException
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/end_event")
    public void endEvent(@RequestParam("eventCode") int eventCode, @RequestParam("hostId") String hostId, @RequestParam("accessToken") String accessToken)
        throws InvalidKeyException
    {
        ((Host)JamBox.getEvent(eventCode).getUser(hostId)).endEvent(accessToken);
    }


    /**
     * API Call to remove a Track frrom the Queue.
     *
     * @param eventCode The code for the Event.
     * @param hostId The ID of the Event's Host.
     * @param trackId The ID of the track to remove.
     * @param accessToken The access token associated with the Event.
     *
     * @return True if the removal is successful, false otherwise.
     *
     * @throws InvalidKeyException
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/remove_track")
    public void removeTrack(@RequestParam("eventCode") int eventCode, @RequestParam("hostId") String hostId, @RequestParam("trackId") String trackId, @RequestParam("accessToken") String accessToken)
        throws InvalidKeyException
    {
        ((Host)JamBox.getEvent(eventCode).getUser(hostId)).removeTrack(trackId, accessToken);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/cycle")
    public void cycle(@RequestParam("eventCode") int eventCode, @RequestParam("hostId") String hostId, @RequestParam("accessToken") String accessToken)
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        ((Host)JamBox.getEvent(eventCode).getUser(hostId)).skipSong(accessToken);
    }

    @ExceptionHandler(value = InvalidKeyException.class)
    public ResponseEntity<Object> exception(InvalidKeyException exception)
    {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<Object> exception(IOException exception)
    {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SpotifyWebApiException.class)
    public ResponseEntity<Object> exception(SpotifyWebApiException exception)
    {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> exception(java.util.NoSuchElementException exception)
    {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
