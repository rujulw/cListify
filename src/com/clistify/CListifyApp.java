package com.clistify;

import com.clistify.service.AuthService;
import com.clistify.service.AuthServiceImpl;
import com.clistify.service.PlaylistService;
import com.clistify.service.PlaylistServiceImpl;
import com.clistify.service.UserInputService;

public class CListifyApp {
    public static void main(String[] args) {
        AuthService authService = new AuthServiceImpl();
        PlaylistService playlistService = new PlaylistServiceImpl();
        UserInputService userInputService = new UserInputService();

        // Login the user
        String username = userInputService.getInput("Enter username: ");
        String password = userInputService.getInput("Enter password: ");
        authService.login(username, password);

        if (authService.isAuthenticated()) {
            // Ask for playlist link and song to add
            String playlistLink = userInputService.getInput("Enter playlist link: ");
            String songDetails = userInputService.getInput("Enter song (e.g. 'Runaway, Kanye West'): ");
            
            // Add the song to the playlist
            playlistService.addSongToPlaylist(playlistLink, songDetails);
        } else {
            System.out.println("Please login to continue.");
        }
    }
}
