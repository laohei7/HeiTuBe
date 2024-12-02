This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop, Server.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.


```shell
https://api.bilibili.com/x/web-interface/popular?ps=20&pn=1&web_location=333.934&w_rid=ec1587b1f3ffa9e187a83393703e2073&wts=1733067033


https://api.bilibili.com/x/web-interface/popular?ps=20&pn=2&web_location=333.934&w_rid=a9cf7d03f1e321c6446cc07aff3617b9&wts=1733067144


https://api.bilibili.com/x/web-interface/popular?ps=20&pn=3&web_location=333.934&w_rid=61e8f13ab53ac84a17089b03b94ffd9a&wts=1733067145


https://api.bilibili.com/x/web-interface/popular?ps=20&pn=4&web_location=333.934&w_rid=671726e45552f774020f7f2a099a3f92&wts=1733067150


https://api.bilibili.com/x/web-interface/popular?ps=20&pn=5&web_location=333.934&w_rid=258d247bf6306a6880c03aebb0052296&wts=1733067159
```