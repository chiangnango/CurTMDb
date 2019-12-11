# CurTMDb

This sample app contains 2 pages, popular movies list and about page. All the data is from *The Movie Database(TMDb)*.

When first time launch the app, it will fetch Configuration data of TMDb and then fetch first page of popular movies list.
Each item contains movie's thumbnail, title, release date, vote average and favorite icon.
Favorite icon is clickable and data is saved locally, taht is local database. 
Because there are a large amount of movies, when scroll to bottom, app will automatically fetch data of further pages.

The app is written in Kotlin and uses Android Architecture Components - **ViewModel, LiveData & Room**, and **Dagger2** for dependency injection.
It also uses **Kotlin Coroutine** and **Retrofit2** to fetch data, and **Paging** library help to handle load and display popular movies list.

For security concern, in project root folder, please create file *apikey.properties* to successfully fetch api:
    
    // apikey.properties
    apikey="xxx"
    
And please create file *signingconfig.properties* to install released apk with proguard enabled:

    // signingconfig.properties
    storeFile=xxx
    storePassword=xxx
    keyAlias=xxx
    keyPassword=xxx

