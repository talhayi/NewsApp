# NewsApp
This project is an Android mobile application built on top of news data provided by NewsAPI.org. The app is developed using the MVVM architecture with Clean Architecture principles. 
It also incorporates popular Android technologies such as Navigation, Dagger Hilt, Retrofit, Room, Glide, and Paging3.

## Features
- **Home Page:** Lists 15 latest news using Paging3. Each news item contains information like image, author, title, and description. When a news item is clicked, it is saved to the Room database.
- **Categorized Listing:** Displays news categorized on the home page. Clicking on a category shows the relevant news for that category.
- **Search Page:** Lists relevant news based on search queries. Search results also include image, author, title, and description similar to the home page.
- **Favorites Page:** Lists favorite news saved in the Room database. Access to favorite news is provided through this page.
- **Detail Page:** Navigates to a detailed page when a news item is clicked. This page provides more in-depth information about the selected news, including additional details and content.

##  Important Libraries in Project

|Library Name|Version Number|
|------------|--------------------------|
|Retrofit |2.9.0|
|Hilt |2.48.1|
|Lifecycle |2.6.2|
|Navigation	Component |2.7.6|
|Coroutines |1.7.1|
|Room |2.6.1|
|Glide |4.13.2|
|Paging3 |3.2.1|

## Images in NewsApp

<img src = "https://github.com/talhayi/NewsApp/assets/56438103/c1730db3-668c-477f-8cd2-3e74ae8e4a15.jpeg" width="250" height="500">  
<img src = "https://github.com/talhayi/NewsApp/assets/56438103/6a102e63-ada5-42ea-a386-5ce98f5be7fd.jpeg" width="250" height="500">  
<img src = "https://github.com/talhayi/NewsApp/assets/56438103/862a2a32-6623-4e2d-9651-3c863c284d89.jpeg" width="250" height="500">  
<img src = "https://github.com/talhayi/NewsApp/assets/56438103/95df7b9b-3143-4a0e-9b5a-74e51fe0f665.jpeg" width="250" height="500">  
