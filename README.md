

## Tapsell Plus Plugin 

Cordova / PhoneGap Plugin for Tapsell Plus.

## Contents

1. [Description](#description)
2. [Features](#features)
3. [Demo](#quick-demo)
4. [Quick Start](#quick-start)
5. [Installation](#installation)
6. [Usage](#usage)
7. [API](#api)
8. [Wiki and Docs](#wiki-and-docs)
9. [Screenshots](#screenshots)
10. [License](#license)
11. [Credits](#credits)

## Description

This Cordova / PhoneGap plugin enables displaying mobile Ads with single line of javascript code. Designed for the use in HTML5-based cross-platform hybrid games and other applications.

## Features

Platforms supported:
- [ ] Amazon-FireOS, via Android SDK (part of Google Play service)
- [x] Android, via Android SDK (part of Google Play service)
- [ ] iOS
- [ ] Windows Phone

Ad Types:
- [x] Banner
- [x] Interstitial (picture, video), highly recommended. :fire:
- [x] Reward Video, highly recommended. :fire:
- [ ] Native Ads (on roadmap)
- [ ] Native Ads Advanced (on roadmap)

## Quick Demo

Wanna quickly see the mobile ad on your simulator or device? Try the following commands.

```bash
    # install cordova CLI
    [sudo] npm install cordova -g

    # install a small utility to run all the commands for you
    [sudo] npm install plugin-verify -g

    # Demo: run tapsell plus demo with sample index.html
    plugin-verify tapsell-plus-cordova-plugin
```

## Quick start
```bash
	# create a demo project
    cordova create test1 com.miladesign.tapsellplus Test1
    cd test1
    cordova platform add android

    # now add the plugin, cordova CLI will handle dependency automatically
    cordova plugin add tapsell-plus-cordova-plugin

    # now remove the default www content, copy the demo html file to www
    rm -r www/*;
    cp plugins/tapsell-plus-cordova-plugin/test/* www/;

	# now build and run the demo in your device or emulator
    cordova prepare; 
    cordova run android;
    # or import into eclipse
```

## Installation

* If use with Cordova CLI:
```bash
cordova plugin add tapsell-plus-cordova-plugin
```

* If use with PhoneGap Build:
```xml
<plugin name="tapsell-plus-cordova-plugin" source="npm"></plugin>
```

Notice:
* If build locally using ```tapsell-plus-cordova-plugin```, to avoid build error, you need install some extras in Android SDK manager (type ```android sdk``` to launch it):
![android extra](https://cloud.githubusercontent.com/assets/2339512/8176143/20533ec0-1429-11e5-8e17-a748373d5110.png)

## Usage

Show Mobile Ad with single line of javascript code.

Step 1: Create new application, in [Tapsell portal](http://www.tapsell.ir/), then write it in your javascript code.

```javascript
TapsellPlus.initialize('AppKey');
```

Step 2: Want cheap and basic banner? single line of javascript code.

```javascript
// it will display small banner at top center, using the default options
TapsellPlus.createBanner(zoneId, TapsellPlus.AD_POSITION.TOP_CENTER, TapsellPlus.AD_SIZE.BANNER_320x50);
```

Step 3: Want interstitial Ad to earn more money ? Easy, 2 lines of code. 

```javascript
// preppare and load ad resource in background, e.g. at begining of game level
TapsellPlus.requestInterstitial(zoneId);

// show the interstitial later, e.g. at end of game level
TapsellPlus.showInterstitial(zoneId);
```

Step 4: Want rewarded video Ad to earn more money ? Easy, 2 lines of code. 

```javascript
// preppare and load ad resource in background, e.g. at begining of game level
TapsellPlus.requestRewardedVideo(zoneId);

// show the interstitial later, e.g. at end of game level
TapsellPlus.showRewardedVideo(zoneId);
```

## API

Methods:
```javascript
// initialize plugin
initialize(appKey);

// use banner
createBanner(zoneId, position, size);
createBannerAtXY(zoneId, x, y, size);
removeBanner();
showBanner();
hideBanner();

// use interstitial
requestInterstitial(zoneId);
showInterstitial(backDisabled);

// use rewarded video
requestRewardedVideo(zoneId);
showRewardedVideo(backDisabled);
```

Events:
```javascript
// response (on Ad Available)
// error (on NoAd Available)
// onError
// onOpened
// onClosed
// onRewarded
document.addEventListener('response', function(e){
    var data = e.detail || e.data || e;
	
	consoleLog("response. adType: " + data.adType);
});
```

## Wiki and Docs

Quick start, simply copy & paste:
* [Example Code](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/1.0-Quick-Start-Example-Code)
* [Complete Demo index.html](https://github.com/vinoosir/tapsell-plus-cordova-plugin/blob/main/test/index.html)

API Reference:
* [API Overview](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/1.1-API-Overview)
* [How to Use Banner](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/1.2-Methods-for-Banner)
* [How to Use Interstitial](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/1.3-Methods-for-Interstitial)
* [How to Use Rewarded Video](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/1.4-Methods-for-Interstitial)
* [How to Handle Ad Events](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/1.5-Events)

Other Documentations:
* [ChangeLog](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/ChangeLog)
* [FAQ](https://github.com/vinoosir/tapsell-plus-cordova-plugin/wiki/FAQ)

## Screenshots

Android Banner | Android Interstitial | Android Rewarded Video
-------|---------------|---------------
![ScreenShot](https://raw.githubusercontent.com/VinoosIr/tapsell-plus-cordova-plugin/main/docs/screenshot_banner.jpg) | ![ScreenShot](https://raw.githubusercontent.com/VinoosIr/tapsell-plus-cordova-plugin/main/docs/screenshot_interstitial.png) | ![ScreenShot](https://raw.githubusercontent.com/VinoosIr/tapsell-plus-cordova-plugin/main/docs/screenshot_rewarded_video.png)


## License

You can use the plugin for free, or you can also pay to get a license. IMPORTANT!!! Before using the plugin, please read the following content and accept the agreement. THIS WILL AVOID POTENTIAL PROBLEM AND DISPUTE.

There are 3 license options, fully up to you:
1. Free and Open Source, no support
2. Commercial, with email/skype support
3. Win-win partnership, with forum support

## Credits

This project is created and maintained by Milad Mohammadi Rezagah.

More Cordova/PhoneGap plugins by Milad Mohammadi Rezagah, [find them in plugin registry](http://plugins.cordova.io/#/search?search=miladesign), or [find them in npm](https://www.npmjs.com/~miladesign).

Project outsourcing and consulting service is also available. Please [contact us](mailto:rezagah.milad@gmail.com) if you have the business needs.