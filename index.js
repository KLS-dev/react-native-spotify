import { NativeModules } from 'react-native';
var RNSpotify = NativeModules.RNSpotify;

const spotify = {
    setup: RNSpotify.setup,
    goToLogin: RNSpotify.goToLogin,
    handleAuthCallbackUrl: RNSpotify.handleAuthCallbackUrl,
    play: RNSpotify.play,
}

export default spotify;
