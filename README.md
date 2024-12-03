# CSC207 Software Design Project

### MusicPal: AI Music Recommendation System :musical_note:

This is the repository for our group project for CSC207 Software Design at the University of Toronto. Our project is a music recommendation system that uses AI to recommend songs based on user preferences. The project is written in Java and uses Swing for the GUI.

The initial code was obtained from https://github.com/CSC207-2024F-UofT/NoteApplication.

### Contributors:

- Felipe Valderrama - [fvm2](github.com/fvm2)
- Ceren Sahin - [CeroSahin](github.com/CeroSahin)
- Genco Erden - [kotodorii](github.com/kotodorii)
- Canberk Soytekin - [KameSanKaro](github.com/KameSanKaro)


## Project Overview

MusicPal is an intelligent music recommendation application that helps users discover new music tailored to their unique
tastes. By leveraging AI-powered recommendation algorithms, the application goes beyond traditional streaming platform
suggestions to provide personalized musical discoveries.

### Purpose
The project was created to solve the common frustration of music listeners who struggle to find new songs that truly
match their individual preferences. Many music platforms offer generic recommendations based on broad categories,
while MusicPal aims to provide more nuanced, personalized suggestions by learning from user interactions and preferences.

### Problem Statement
Music discovery can be overwhelming and time-consuming. Users often find themselves:

- Stuck listening to the same artists and genres
- Overwhelmed by massive music libraries
- Dissatisfied with algorithm-generated recommendations that feel impersonal

MusicPal addresses these challenges by:

- Allowing users to explicitly define their music preferences
- Using AI to generate targeted recommendations
- Enabling users to provide feedback that continuously refines future suggestions

## Features
### User Management

- Secure user registration and login system
- Personal profile creation with basic user information
- Password-protected accounts to ensure privacy

#### ss of login signup.

### Music Preference Customization

- Detailed preference input for:

    - Songs
    - Genres
    - Artists
    - Albums

- Ability to specify multiple preferences in each category
- Easy-to-use text-based input interface

#### ss of the preferences.

### AI-Powered Recommendations

- Personalized music recommendations generated using OpenAI technology
- Recommendation types focused on songs
- Like/Dislike options for each recommendation

#### ss of recommendation.

### Recommendation History

- View past music recommendations
- Track liked and disliked suggestions
- Monitor recommendation evolution over time

#### ss of history.

### User Experience

- Simple, intuitive graphical interface
- Card-based navigation between different application screens
- Quick access to key features like profile, preferences, and recommendations

### Technical Features

- Uses Java Swing for GUI
- Integrates with OpenAI for intelligent recommendations
- Modular design allowing easy future expansions

### Example Workflow

1. Create an account
2. Set your music preferences
3. Request a recommendation
4. Like or dislike the suggested song
5. Watch your recommendations improve over time

#### Screenshots will be added shortly.

### Ideal For

- Music enthusiasts seeking personalized discovery
- Users tired of generic streaming platform recommendations
- People interested in expanding their musical horizons


## Feedback and Contributions
We value your input! Please use our MusicPal Feedback Google Form to share your thoughts, suggestions, and experiences with the application.
### Feedback Guidelines
#### What We Want:

- Specific, constructive comments
- Detailed descriptions of user experiences
- Suggestions for new features
- Reported bugs or performance issues
- Accessibility improvement recommendations

#### Feedback Categories:

- User Interface
- Recommendation Accuracy
- Feature Suggestions
- Technical Performance
- Accessibility Concerns

#### Submission Requirements

- Be clear and concise
- Provide specific examples
- Include steps to reproduce any issues
- Share your device and software version

#### What to Expect:

- Acknowledgment of feedback within 5-7 business days
- Potential follow-up questions for clarification
- Updates on how your feedback might be implemented
- No guarantee of immediate feature implementation

### Contribution Guidelines
#### For Developers:

- Fork the repository
- Create a detailed pull request
- Follow existing code style
- Include comprehensive tests
- Describe proposed changes thoroughly

#### Reporting Bugs:

- Use GitHub Issues
- Provide detailed reproduction steps
- Include system configuration
- Attach relevant logs or screenshots

#### Contact:
GitHub Discussions: Project Discussion Board

Note: We reserve the right to moderate and select feedback for implementation based on project goals and resources.

## Package Structure (for backend summary)

### 1. Entity Layer (entity)
Core business objects representing the domain model.

#### User.java

- Represents system users
- Key attributes: id, name, surname, email, country, password, thread (OpenAI chat thread)

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
