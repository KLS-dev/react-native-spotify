package com.kls.RNSpotify;

import android.util.Log;
import android.net.Uri;
import java.util.Map;

import android.content.Intent;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;


public class RNSpotifyManager extends ReactContextBaseJavaModule implements
        Player.NotificationCallback, ConnectionStateCallback{

    public ReactApplicationContext reactContext;
    private SpotifyPlayer mPlayer;

    public RNSpotifyManager(ReactApplicationContext _reactContext) {
        super(_reactContext);

        reactContext = _reactContext;
    }

    @Override
    public String getName() {
        return "RNSpotify";
    }

    @ReactMethod
    public void setup(String clientId, Promise promise) {

    }



    @ReactMethod
    public void handleAuthCallbackUrl(String url, Callback callback) {
        Uri uri = Uri.parse(url);
        AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
        if (response.getType() == AuthenticationResponse.Type.TOKEN) {
            Config playerConfig = new Config(reactContext.getCurrentActivity(), response.getAccessToken(), "19ff9062336c4833aa842c19d7bd968e");
            Spotify.getPlayer(playerConfig, reactContext.getCurrentActivity(), new SpotifyPlayer.InitializationObserver() {
                @Override
                public void onInitialized(SpotifyPlayer spotifyPlayer) {
                    mPlayer = spotifyPlayer;
                    mPlayer.addConnectionStateCallback(RNSpotifyManager.this);
                    mPlayer.addNotificationCallback(RNSpotifyManager.this);
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                }
            });
        }
    }

    @ReactMethod
    public void goToLogin(String url) {
        AuthenticationRequest.Builder builder =
        new AuthenticationRequest.Builder("19ff9062336c4833aa842c19d7bd968e", AuthenticationResponse.Type.TOKEN, url);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginInBrowser(reactContext.getCurrentActivity(), request);
    }

    @Override
    public void onLoggedIn() {
        mPlayer.playUri(null, "spotify:track:2TpxZ7JUBn3uw46aR7qd6V", 0, 0);
    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(int i) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {
    }
}
