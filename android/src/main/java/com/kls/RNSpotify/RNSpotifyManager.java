package com.kls.RNSpotify;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNSpotifyManager extends ReactContextBaseJavaModule {

    public RNSpotifyManager(ReactApplicationContext _reactContext) {
        super(_reactContext);
    }

    @Override
    public String getName() {
        return "RNSpotify";
    }

    @ReactMethod
    public void setup(String clientId, Promise promise) {

    }
}
