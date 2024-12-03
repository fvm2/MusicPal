# CSC207 Software Design Project

###  Presentation Slides: 
[slides](https://docs.google.com/presentation/d/1dzptTVQtqOvaQz15DaR7YzDP-jL7LxhHNIqMRIsJRa0/edit?usp=sharing)

### MusicPal: AI Music Recommendation System :musical_note:

This is the repository for our group project for CSC207 Software Design at the University of Toronto. Our project is a music recommendation system that uses AI to recommend songs based on user preferences. The project is written in Java and uses Swing for the GUI.

The initial code was obtained from https://github.com/CSC207-2024F-UofT/NoteApplication.

### Team Members:

- Felipe Valderrama - [fvm2](github.com/fvm2)
- Ceren Sahin - [CeroSahin](github.com/CeroSahin)
- Genco Erden - [kotodorii](github.com/kotodorii)
- Canberk Soytekin - [KameSanKaro](github.com/KameSanKaro)


## Backend by Felipe

I tried to simplify things by implementing the whole backend logic, so you guys can focus on the frontend user interaction,
for this I provided an API using public methods in different classes. I had to think about the whole process logic first,
and when I had a clear idea, I made a sketch of the different classes with their attributes and methods, this is the documentation needed
for you guys to use the logic from the frontend, I also will try to add as many comments and javadocs to my code.

### Domain Layer

1. Create Users in registration:

```java
User user = new User("John", "Doe", "john@email.com", "USA", "rawPassword");
```

2. Create/Update preferences:

```java
Preference pref = new Preference();
pref.addSong("Bohemian Rhapsody by Queen");
pref.addArtist("Pink Floyd");
pref.addAlbum("Abbey Road by The Beatles");
pref.addGenre("Progressive Rock");
```

3. Handle recommendations:

```java
Recommendation rec = new Recommendation(userId, "Dark Side of the Moon by Pink Floyd", "Album", 0);
rec.rate(true);  // User likes the recommendation
```

### Infrastructure.Database Layer

1. User Registration:

```java
UserRepository userRepo = new UserRepository();
User newUser = new User("John", "Doe", "john@email.com", "USA", "password123");
userRepo.save(newUser);
```

2. User Login:

```java
// In a real use case, we would compare the inputs from the GUI.
boolean isValid = userRepo.verifyPassword("john@email.com", "password123");
```

3. Find a User:

```java
Optional<User> user = userRepo.findByEmail("john@email.com");
```

4. Update User:

```java
user.ifPresent(u -> {
    u.setCountry("Canada");
    userRepo.update(u);
});
```

## Package Structure

### 1. Entity Layer (entity)
   Core business objects representing the domain model. 
   
#### User.java

- Represents system users
- Key attributes: id, name, surname, email, country, password, thread (OpenAI chat thread), friends
- Methods for friend list management

#### Preference.java

- Stores user music preferences
- Key attributes: userId, preferenceId, songs, genres, artists, albums
- Methods for adding/managing preferences

#### Recommendation.java

- Represents music recommendations
- Key attributes: recommendationId, userId, content, type (Song/Album/Artist), by (0 for AI, userId for friend), liked
- Methods for rating recommendations

### 2. DTO Layer (dto)
   Data Transfer Objects for safe data transfer between layers.

#### UserDTO: User registration data
#### LoginDTO: Login credentials
#### PreferenceDTO: Music preferences data
#### RecommendationDTO: Recommendation data
#### FriendRequestDTO: Friend request data

### 3. Service Layer (service)
   Business logic implementation. 
   
#### OpenAIService.java

- Manages OpenAI API interactions
- Key methods:

```java
initialize(): Creates AI assistant
createThread(): Creates user thread
getRecommendationsFromAI(String input, String threadId): Gets AI recommendations
```


#### UserService.java

- Handles user operations
- Key methods:

```java
register(UserDTO): User registration
login(String email, String password): User authentication
sendFriendRequest(int fromId, int toId): Friend request management
```

#### PreferenceService.java

- Manages music preferences
- Key methods:

```java
updatePreferences(PreferenceDTO): Updates user preferences
```

#### RecommendationService.java

- Handles recommendation logic
- Key methods:

```java
getRecommendation(int userId, String type): Gets new recommendations
rateRecommendation(int recId, boolean liked): Rates recommendations
```

#### Result.java

- Generic wrapper for operation results
- Provides success/failure status and error handling

### 4. Infrastructure Layer (infrastructure.database)
   Data access and persistence. 
   
#### DatabaseConnection.java

- Manages database connections using HikariCP
- Implements connection pooling

#### IRepository.java

- Generic repository interface
- CRUD operations: findById, save, update, delete

#### Concrete Repositories

- UserRepository: User data operations
- PreferenceRepository: Preference data operations
- RecommendationRepository: Recommendation data operations

## Database Schema:
```sql
CREATE TABLE users (
id SERIAL PRIMARY KEY,
name VARCHAR(255),
surname VARCHAR(255),
email VARCHAR(255) UNIQUE,
country VARCHAR(255),
password VARCHAR(255),
thread VARCHAR(255),
friends TEXT
);

CREATE TABLE preferences (
preference_id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id),
songs TEXT,
genres TEXT,
artists TEXT,
albums TEXT
);

CREATE TABLE recommendations (
recommendation_id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id),
content TEXT,
type VARCHAR(50),
by INTEGER,
liked BOOLEAN
);
```
