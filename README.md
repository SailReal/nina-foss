NINA FOSS is not related to the official "NINA - Die Warn-App des BBK" but provides a similar solution but is free and open source software without Google dependencies: https://nina-foss.de/

NINA FOSS uses the API of the "Bundesamt für Bevölkerungsschutz" to receive messages. As NINA FOSS doesn't use Google Play Services, the app needs to poll the server to detect changes (you can specify in the settings, how often the app connects to the server).

We don't provide any kind of warranty or guarantee that this app will notify you now or in the future, use it at your own risk.

NINA FOSS is based on the [Cryptomator for Android](https://github.com/cryptomator/android) source code.

## Building

### Dependencies

* Git
* JDK 11
* Gradle

### Run Git and Gradle

```
./gradlew assembleApkstoreDebug
```

## Contributing

Please read our [contribution guide](.github/CONTRIBUTING.md), if you would like to report a bug, ask a question, translate the app or help us with coding.

Please make sure before creating a PR, to apply the code style by executing reformat code with optimize imports and rearrange code enabled. The best way to do this is to create a macro for it in android studio and set it to the save shortcut.

## Code of Conduct

Help us keep NINA FOSS open and inclusive. Please read and follow our [Code of Conduct](.github/CODE_OF_CONDUCT.md).

## License

This project is dual-licensed under the GPLv3 for FOSS projects as well as a commercial license for independent software vendors and resellers. If you want to modify this application under different conditions, feel free to contact our support team.
