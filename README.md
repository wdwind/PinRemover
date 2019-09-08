# Certificate Pin Remover

Android Xposed module to remove okhttp3 certificate pinning.

## Testing

Tested on Twitter 7.79.0-release.27. Change the [package name](https://github.com/wdwind/PinRemover/blob/07bae04936269f72f8f4c9ea3bdf6abea1dfbf7e/app/src/main/java/wddd/android/xposed/pinremover/Main.java#L15) to test other apps.

## Install

* [Optional] Suggest to test in Genymotion emulator
* Root the device
* Install Xposed framework
* Download the apk from [releases](https://github.com/wdwind/PinRemover/releases)
* Install the apk `adb install PinRemover.apk`

## License

MIT

## Disclaimer

Use it at your own risk.

