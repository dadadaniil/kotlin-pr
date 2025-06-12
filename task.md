Realize business logic for work with a network, what it is necessary to do:

- According to the documentation https://developers.thecatapi.com/view-account/ylX4blBYT9FaoVd6OhvR?report=bOoHBz-8t realize data model CatImageModel

- Realize CatApiService interface for work with Retrofit library

- Realize CatRepository

- Implement NetworkModule to provide Retrofit service, at intermediate steps we need to provide Json serializer, OkHttp client and Retrofit itself.

- Implement ViewModel logic

- See how the library for loading pictures in CatCard - AsyncImage
Refactor the project:

- Add domain layer with use case and clean architecture

- Add Dependency injection via Hilt (NetworkModule should be redesigned)
* Extend the functionality of the project (For those who were not at the lecture!):

- Add pagination(infinite ribbon, when user scrolls the ribbon new pictures are loaded automatically) May have to use a slightly different API 