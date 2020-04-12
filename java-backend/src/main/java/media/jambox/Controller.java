package media.jambox;

import java.security.InvalidKeyException;
import media.jambox.model.Event;
import media.jambox.model.Host;
import media.jambox.model.JamBox;
import media.jambox.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Controller
{
    @RequestMapping("/api/add_event")
    public int newEvent(@RequestParam("playlistId") String playlistId, @RequestParam("accessToken") String accessToken, @RequestParam("hostId") String hostId)
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        return JamBox.addEvent(playlistId, accessToken, hostId, -1, null);
    }

    @RequestMapping("/api/event")
    public Event event(@RequestParam("eventCode") int eventCode)
    {
        return JamBox.getEvent(eventCode);
    }

    @RequestMapping("/api/add_user")
    public void addUser(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        JamBox.getEvent(eventCode).addUser(userId);
    }

    @RequestMapping("/api/user")
    public User user(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        return JamBox.getEvent(eventCode).getUser(userId);
    }

    @RequestMapping("/api/upvote")
    public void upvote(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        JamBox.getEvent(eventCode).getUser(userId).changeVote(trackId, 1);
    }

    @RequestMapping("/api/downvote")
    public void downvote(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        JamBox.getEvent(eventCode).getUser(userId).changeVote(trackId, -1);
    }

    @RequestMapping("/api/request")
    public boolean request(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        return JamBox.getEvent(eventCode).getUser(userId).requestTrack(trackId);
    }

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
     */
    @RequestMapping("/api/end_event")
    public boolean endEvent(@RequestParam("eventCode") int eventCode, @RequestParam("hostId") String hostId, @RequestParam("accessToken") String accessToken)
    {
        try
        {
            ((Host)JamBox.getEvent(eventCode).getUser(hostId)).endEvent(accessToken);
            return true;
        }
        catch (InvalidKeyException e)
        {
            return false;
        }
    }

    @RequestMapping("/api/remove_track")
    public void removeTrack(@RequestParam("eventCode") int eventCode, @RequestParam("hostId") String hostId, @RequestParam("trackId") String trackId, @RequestParam("accessToken") String accessToken)
    {
        ((Host)JamBox.getEvent(eventCode).getUser(hostId)).removeTrack(trackId, accessToken);
    }
}
