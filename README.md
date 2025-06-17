# Open Library Android 📚

**Open Library Android** is a modern Android app built using Jetpack Compose that allows users to explore a list of the "latest" books published in 2025, sourced from the OpenLibrary API. 
Users can view detailed information about each book and manage their own wishlist of favorite titles. 
The app implements state-of-the-art Android development principles such as the MVVM architecture, single-activity design, and Jetpack Compose-first UI development.

## Features ✨

- **Fetch Latest Books**: Fetches a list of books published in 2025 from the OpenLibrary API at user request.
- **Book Details**: View detailed information about each book, including:
    - Title
    - Authors
    - Publisher
    - ISBN
    - Publish date
    - Cover image
- **Wishlist Management**: Add or remove books from a personal wishlist.
- **Caching for Offline Access**:
    - Book data is cached using Room, so you can revisit the app without needing to fetch data again.
    - Images are cached with Coil, ensuring smooth image loading and reduced network usage.
- **Responsive Design**: Fully responsive and scrollable screens, ensuring no content is clipped on small or large screens.
- **Haptic Feedback**: Provides subtle haptic feedback for certain interactions, enhancing the user experience.
- **Pull-to-Refresh**: Easily refresh the book list with an intuitive pull-to-refresh gesture.

## Technical Details 🛠️

### Architecture
- **MVVM (Model-View-ViewModel)**: Ensures a clean separation of concerns between UI and business logic, making the app scalable and maintainable.
- **Single-Activity Design**: The app follows the single-activity principle, using a single `MainActivity` to host all screens and navigation.
- **Jetpack Compose**: The app’s UI is entirely built using Jetpack Compose, Android's modern toolkit for building native UIs.

### Navigation
- **NavHost & Navigation Graph**: The app uses Jetpack Navigation Compose to manage screen transitions and navigation, ensuring seamless user flow.

### Libraries & Dependencies
- **Room**: Used for local caching of book data, ensuring offline access and fast loading times.
- **Hilt**: Simplifies dependency injection, making the app modular and testable.
- **Coil**: Handles image loading and caching with built-in support for Compose.
- **Kotlin Coroutines & Flows**: Enables asynchronous programming and reactive UI updates.
- **Material 3 (Material You)**: Provides a sleek and modern UI design with customizable themes.

### API
- **OpenLibrary API**: The app fetches data from OpenLibrary's public API for book details.

## Device Compatibility 📱

This app has been tested on a **Samsung Galaxy S21 Ultra**. Compatibility with other devices is not guaranteed.

## How to Run the Project 🚀

1. Clone this repository:
    ```bash
    git clone <repository-url>
    ```
2. Open the project in Android Studio (Giraffe or later).
3. Build the project to install the required dependencies.
4. Run the app on an emulator or a physical device.

## Future Improvements 🛠️

Here are some features and enhancements we may consider in the future:
- **Search Functionality**: Allow users to search for books by title, author, or genre.
- **Wishlist Menu**: View and edit what is on your wishlist from a separate screen.
- **Dark Mode**: Improve dark mode support for enhanced usability.
- **Testing**: Add robust unit and instrumented tests for better code quality and reliability.

## License 📄

This project is a private application that I mostly use as portfolio showcase.
It is not open to public contribution or distribution.

## Acknowledgements 🙏

- [OpenLibrary API](https://openlibrary.org/) for providing the book data.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for making modern Android UI development a joy.
- [Material Design](https://material.io/) for design inspiration.