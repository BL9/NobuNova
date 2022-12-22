package gab.Utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

public class TwitchHelper {
    private static TwitchHelper instance = null;
    private static final String CLIENT_ID = "tcwc290j51bx5axnb6egfwx30fettf";
    private static final String API_BASE_URL = "https://api.twitch.tv/helix/";

    private final String channelName;
    private final String token;

    private LocalDateTime streamingSince;
    private LocalDateTime lastStreamingCheck;

    private TwitchHelper() {
        channelName = null;
        token = null;
    }

    private TwitchHelper(ConfigHelper configHelper) {
        this.channelName = configHelper.getValue("channel").substring(1);
        this.token = configHelper.getValue("oauth_token").substring(6);
    }

    public static TwitchHelper getInstance(ConfigHelper configHelper) {
        if(instance == null)
            synchronized(TwitchHelper.class) {
                if(instance == null)
                    instance = new TwitchHelper(configHelper);
            }
        return instance;
    }
    public static TwitchHelper getInstance() {
        if(instance == null)
            synchronized(TwitchHelper.class) {
                if(instance == null)
                    instance = new TwitchHelper();
            }
        return instance;
    }

    public LocalDateTime getStreamingSince() throws IOException, InterruptedException {
        if(streamingSince == null || lastStreamingCheck.plusMinutes(10).isBefore(LocalDateTime.now()))
        {
            String uri = API_BASE_URL + "streams?user_login=" + channelName;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder(URI.create(uri))
                .header("Authorization", "Bearer " + token)
                .header("Client-Id", CLIENT_ID)
                .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());            

            if(res.statusCode() == 200)
            {
                JSONObject resBody = new JSONObject(res.body());
                JSONArray data = resBody.getJSONArray("data");

                if(data.length() > 0)
                    streamingSince = LocalDateTime.parse(data.getJSONObject(0).getString("started_at"), DateTimeFormatter.ISO_DATE_TIME);
                else
                    streamingSince = null;
            }
            else
            {
                streamingSince = null;
            }

            lastStreamingCheck = LocalDateTime.now();
        }

        return streamingSince;
    }
}
