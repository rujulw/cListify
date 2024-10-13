package com.clistify.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpotifyClient {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    
    private String accessToken;

    // Method to request an access token using the Client Credentials flow
    public void authenticate() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(TOKEN_URL);

            // Set authorization header (Base64 encoded client_id:client_secret)
            String auth = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedAuth = new String(java.util.Base64.getEncoder().encode(auth.getBytes()));
            post.setHeader("Authorization", "Basic " + encodedAuth);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            // Add form parameters
            post.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(
                    java.util.Arrays.asList(
                            new org.apache.http.message.BasicNameValuePair("grant_type", "client_credentials")
                    )
            ));

            // Execute the request
            HttpResponse response = httpClient.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());

            // Parse the JSON response to extract the access token
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> responseMap = mapper.readValue(responseBody, HashMap.class);
            accessToken = responseMap.get("access_token");

            System.out.println("Authenticated successfully! Access token: " + accessToken);
        } catch (IOException e) {
            System.err.println("Error during authentication: " + e.getMessage());
        }
    }

    // Getter for the access token
    public String getAccessToken() {
        return accessToken;
    }

    public String searchSong(String songDetails) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String searchUrl = "https://api.spotify.com/v1/search?q=" + songDetails.replace(" ", "%20") + "&type=track&limit=1";

            HttpPost post = new HttpPost(searchUrl);
            post.setHeader("Authorization", "Bearer " + accessToken);

            HttpResponse response = httpClient.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());

            // Parse JSON response to get track ID
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(responseBody, HashMap.class);

            // Extract the first track ID from the response
            String trackId = ((Map<String, Object>) ((Map<String, Object>) ((java.util.List<Object>) ((Map<String, Object>) responseMap.get("tracks")).get("items")).get(0)).get("id"));

            System.out.println("Found track: " + trackId);
            return "spotify:track:" + trackId;
        } catch (IOException e) {
            System.err.println("Error during song search: " + e.getMessage());
            return null;
        }
    }

    public void addSongToPlaylist(String playlistLink, String songUri) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String playlistId = playlistLink.split("/playlist/")[1];  // Extract playlist ID from link
            String addTrackUrl = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks?uris=" + songUri;
    
            HttpPost post = new HttpPost(addTrackUrl);
            post.setHeader("Authorization", "Bearer " + accessToken);
            post.setHeader("Content-Type", "application/json");
    
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 201) {
                System.out.println("Song added successfully!");
            } else {
                System.err.println("Error adding song to playlist.");
            }
        } catch (IOException e) {
            System.err.println("Error during song addition: " + e.getMessage());
        }
    }
    
}
