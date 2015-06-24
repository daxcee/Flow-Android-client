#Flow Android client ![](https://circleci.com/gh/srmds/Flow-Android-client/tree/master.svg?style=shield&circle-token=982140173ef2b98794c97ed9cfa17d90cddc17bf)

Android Client that connects and persists data retrieved from the [Flow API](https://github.com/srmds/FlowAPI)
![screenshot eventoverview](http://i.imgur.com/7HR4uCy.png)

##Prerequisites

[JavaSDK 1.8u45+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
	
[Intellij IDEA 14+](https://www.jetbrains.com/idea/)  or [Android Studio 141.+](https://developer.android.com/sdk/index.html)
	
[Genymotion 2.4.+](https://www.genymotion.com)

## Google Play Services (for now not mandatory)

In order to have Google Play services available we need to install an ARM translator and a flashed image of Google Apps, since these are not available on Genymotion VM's.

Tested images: 

- `Custom phone 5.1.0 - API 22`
- `Google Nexus 6 - 5.1.0 - API 22`

*ARM Translation Installer v1.1*
[download](https://www.androidfilehost.com/?fid=23252070760974384)

*Android 5.1 image - gapps-L-4-21-15*
[download](https://www.androidfilehost.com/?fid=96039337900114811)

### Installlation steps

- Open a Genymotion VM and go to the Homescreen
 
- Drag & drop the ARM-Translation.zip onto the Genymotion VM window. When finished,
close the emulator and start a new instance of the virtual device. 
 
- Once device is restarted and the homescreen is visible,
drag & drop the gapps-version.zip onto the Genymotion VM window. When finished,
close the emulator and start a new instance of the virtual device. 
 
- Open the Google Play Store. Sign-in via email.

- In Play Store go to apps, update any outdated app. When finished,
close the emulator and start a new instance of the virtual device. 

Note that in the install process, there could be notifications of `google apps not working`, ignore those messages. 

### Build & Run

This project uses a [Gradle SDK Manager Plugin](https://github.com/JakeWharton/sdk-manager-plugin). On initial run, the Android SDK needs to be downloaded first, by running:

	$ ./gradlew clean assemble

