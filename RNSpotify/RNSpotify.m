//
//  RNSpotify.m
//  RNSpotify
//
//  Created by Cesar Landeros Delgado on 9/25/16.
//  Copyright © 2016 kls. All rights reserved.
//

#import "RNSpotify.h"
#import "RCTUtils.h"

@interface RNSpotify ()

@property NSString *clientId;
@property (nonatomic, strong) SPTSession *session;
@property (nonatomic, strong) SPTAudioStreamingController *player;

@end

@implementation RNSpotify

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(setup:(NSString *) clientId)
{
    _clientId = clientId;
    [[SPTAuth defaultInstance] setClientID:self.clientId];
}
RCT_EXPORT_METHOD(goToLogin:(NSString *) url)
{
    [[SPTAuth defaultInstance] setRedirectURL:[NSURL URLWithString:url]];
    [[SPTAuth defaultInstance] setRequestedScopes:@[SPTAuthStreamingScope]];

    NSURL *loginURL = [[SPTAuth defaultInstance] loginURL];

    [RCTSharedApplication() openURL:loginURL];
}

RCT_EXPORT_METHOD(handleAuthCallbackUrl:(NSString *) eventUrl callback:(RCTResponseSenderBlock) callback)
{
    NSURL *url = [NSURL URLWithString:eventUrl];

    if ([[SPTAuth defaultInstance] canHandleURL:url]) {
        [[SPTAuth defaultInstance] handleAuthCallbackWithTriggeredAuthURL:url callback:^(NSError *error, SPTSession *session) {
            if (error != nil) {
                NSLog(@"*** Auth error: %@", error);
                callback(@[error]);
                return;
            }

            [self loginUsingSession:session];
            callback(@[[NSNull null]]);
        }];
    }
}

RCT_EXPORT_METHOD(play:(NSString *) songId)
{
    NSString *uri = [NSString stringWithFormat:@"spotify:track:%@", songId];
}

-(void)loginUsingSession:(SPTSession *)session {
    // Get the player instance
    self.player = [SPTAudioStreamingController sharedInstance];
    self.player.delegate = self;
    // Start the player (will start a thread)
    [self.player startWithClientId:self.clientId error:nil];
    // Login SDK before we can start playback
    [self.player loginWithAccessToken:session.accessToken];
}

- (void)audioStreamingDidLogin:(SPTAudioStreamingController *)audioStreaming {
    NSURL *url = [NSURL URLWithString:@"spotify:track:58s6EuEYJdlb0kO7awm3Vp"];
    NSLog(@"cool");
    [self.player playSpotifyURI:@"spotify:track:2waU8KeUmIb9eVfxkHGjvI"
              startingWithIndex:0
           startingWithPosition:10
                       callback:^(NSError *error) {
                           if (error != nil) {
                               NSLog(@"*** failed to play: %@", error);
                               return;
                           }
                       }];

}

-(void)audioStreaming:(SPTAudioStreamingController *)audioStreaming
      didReceiveError:(SpErrorCode)errorCode
             withName:(NSString *)name
{
    NSLog(name);
}

@end
