package com.clistify.service;

import com.clistify.client.SpotifyClient;

public class PlaylistServiceImpl implements PlaylistService {
    private SpotifyClient spotifyClient;

    public PlaylistServiceImpl() {
        this.spotifyClient = new SpotifyClient();
        spotifyClient.authenticate();  // Authenticate with Spotify API
    }

    @Override
    public void addSongToPlaylist(String playlistLink, String songDetails) {
        // Use SpotifyClient to search for the song
        String songUri = spotifyClient.searchSong(songDetails);
        
        // Add the song to the playlist using its URI
        if (songUri != null) {
            spotifyClient.addSongToPlaylist(playlistLink, songUri);
        } else {
            System.err.println("Could not find the song.");
        }
    }
}
