## Lokal Assignment 
This repo contains the source code as well as video demo and APK [here](https://github.com/IzhanAli/LokalAssognment/tree/main/demo)

### Video demo: 
https://github.com/IzhanAli/LokalAssignment/blob/main/demo/Final.mp4

The above screen recording demonstrates the app and when we scroll the data is loaded with pagination from our `JobPagingSource` factory asynchronously. When we open a listing detailed information is shown to the user and if we want to save a listing offline we can tap save and that listing will be stored in Room database (here the JobDetail object is converted to String using Gson and saved) for offline viewing

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
- Error messages


### Functional Requirements [<i>Completed</i>]

✅ On opening the app, users should be presented with a bottom navigation UI with “Jobs” and “Bookmarks” as the sections

✅ The Jobs screen should fetch data from the above API in an paginated approach. Show the title, location, salary and phone data in each card.

✅ Clicking on a Job card should show more details related to it on another screen.

✅ A user should be able to bookmark a Job and this will appear in the “Bookmarks” tab.

✅ All bookmarked Jobs should be stored in a database for offline viewing.

✅ Maintain appropriate states for loading, error, empty etc throughout the app.
