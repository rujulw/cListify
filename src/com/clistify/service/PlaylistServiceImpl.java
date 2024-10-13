package com.clistify.service;

public class PlaylistServiceImpl implements PlaylistService {
    
    @Override
    public void addSongToPlaylist(String playlistLink, String songDetails) {
        // Mock adding song to playlist (this would interact with Spotify's API in the real implementation)
        System.out.println("Adding '" + songDetails + "' to playlist: " + playlistLink);
        // In real implementation, use SpotifyClient to add the song via Spotify's API
    }
}
