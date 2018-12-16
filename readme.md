# SnapClone

A social media messaging app that allows users to easily send pictures and messages. In-app functionality allows users to edit pictures before sending giving users the ability to add stickers around their face and captions anywhere on the image.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
1.) Install Java 8
2.) Install Android Studio 

Installation instructions will vary based on your OS

```

### Setting up Android Studio

for the OpenCV library, you may need a special toolchain version.

1.) Go to Tools > SDK Manger > SDK Platform and click the checkbox by Android 8.0 and Android 5.0

2.) Go to Tools > SDK Manager > SDK Tools and click the checkbox by CMake and NDK then click "Apply"

3.) Go to [this link](https://developer.android.com/ndk/downloads/older_releases) download android-ndk-r17c-*your-operating-system*.zip

4.) Extract the zip folder and copy toolchain from **path-to-android-ndk-r17c**/toolchains/**necessary-toolchain** to **your-android-studio-install-location**/Sdk/ndk-bundle/toolchains

5.) Follow any prompts to update gradle / build tools / sdk

6.) Go to File > Project Structure and under Project change Android Plugin Version to Version 3.1.3

7.) Go to File > Sync Project with Gradle Files

8.) Go to Build > Make Project

9.) Run > app


## Built With

* [Android Studio](https://developer.android.com/studio/) - Android IDE
* [Node.js](https://nodejs.org/en/) - Serverside
* [Volley](https://developer.android.com/training/volley/) - HTTPS protocol for android
* [MySQL](https://www.mysql.com/) - Used to communicate with databases

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).

## Authors

* **Akira Demoss** - _OpenCV, Volley_ - [akirademoss](https://github.com/akirademoss)
* **Quinn Salas** - _Backend, UI_ - [qsalas](https://github.com/qsalas)
* **Michael Lauderback** - _Volley, User Interface, MySQL_ - [theMike97](https://github.com/theMike97)


## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
