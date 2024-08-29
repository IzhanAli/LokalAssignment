## Lokal Assignment 
This repo contains the source code as well as video demo and APK [here](https://github.com/IzhanAli/LokalAssognment/tree/main/demo)

### Video demo: https://github.com/IzhanAli/LokalAssognment/raw/main/demo/Screen_recording_20240828_181238.mp4

### Tech Stack:
- Kotlin as programming language
- MVVM as architecture
- Room for offline storage
- Retrofit for REST API data fetch
- Material3 as design language
- Shimmer for loading animation
- BottomNavigation for navigation

Further scope:
- Address edge cases
- State Management


### Functional Requirements [<i>Completed</i>]

✅ On opening the app, users should be presented with a bottom navigation UI with “Jobs” and “Bookmarks” as the sections

✅ The Jobs screen should fetch data from the above API in an paginated approach. Show the title, location, salary and phone data in each card.

✅ Clicking on a Job card should show more details related to it on another screen.

✅ A user should be able to bookmark a Job and this will appear in the “Bookmarks” tab.

✅ All bookmarked Jobs should be stored in a database for offline viewing.

✅ Maintain appropriate states for loading, error, empty etc throughout the app.
