package com.kls.RNSpotify;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class RNSpotifyManager extends ReactContextBaseJavaModule {

    public RNSpotifyManager(ReactApplicationContext _reactContext) {
        super(_reactContext);
    }

    @Override
    public String getName() {
        return "RNSpotifyManager";
    }

}
