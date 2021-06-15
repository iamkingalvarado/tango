# Tango Challenge

This project was made exclusively for a test at [Tango](https://tango.io)

## Architecture

This project is in **MVVM**. Each layer is separated as best practices demands, data, domain and view en separate folders and no one knows about the other. Communication is through interfaces and everything is stick together via Dependency Injection. Organization is made by **Package by Feature** philosophy so this can be easily scaled to many other features

### Features

- See latest, popular and upcoming movies from [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction)
- See movie detail from [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction)
- Night Mode! Change your phone configuration and see the magic
- Offline support with Room. Once the application downloaded at least the first time the data, subsequent calls can be done from cache even without internet connection
- Dependency Injection through Dagger Hilt
- Networking layer using coroutines flow

## Built With

Tools used for this challenge


* [Retrofit](https://square.github.io/retrofit/) - The networking library used
* [Gson](https://github.com/google/gson) - Serialization and Deserialization tool
* [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Glued everything with this DI framework
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Async programming for the win
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Persistance library
* [AndroidX](https://developer.android.com/jetpack/androidx) - Supposed to handled UI fragmentation but who knows


## Authors

* **Daniel García Alvarado** - [@iamlordalvarado](https://instagram.com/iamlordalvarado)

