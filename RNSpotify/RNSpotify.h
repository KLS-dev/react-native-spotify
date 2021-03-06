//
//  RNSpotify.h
//  RNSpotify
//
//  Created by Cesar Landeros Delgado on 9/25/16.
//  Copyright © 2016 kls. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Spotify/Spotify.h>
#import "RCTBridgeModule.h"

@interface RNSpotify : NSObject <RCTBridgeModule, SPTAudioStreamingDelegate>

@end
