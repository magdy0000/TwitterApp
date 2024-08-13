
Tweet Character Counter

Description

This project provides a UI component designed to count characters in a tweet, ensuring it adheres to Twitter's character limit. It also integrates with the Twitter SDK/API to post tweets directly from the app.

Requirements

Your mission is to create a UI component that can accurately count user input compared to Twitter's character limit.

Key Points
Not all characters are counted as one: Twitter treats some characters differently (e.g., CJK characters).
UI Component Extraction: The character counter UI component is modular and extracted into a separate package.
Twitter Integration: The component integrates with Twitter's SDK/API for real-time tweet posting.
Technologies Used

Architecture: MVI (Model-View-Intent)
Dependency Injection: Dagger Hilt
Design Pattern: Clean Architecture
Asynchronous Programming: Kotlin Coroutines and Flows
UI Binding: ViewBinding
Testing: Unit Tests
Authentication: OAuth 1.0 for handling Twitter authentication
Features

Character Counting: Accurately counts characters considering Twitter's specific rules.
Modular Design: The UI component is reusable and encapsulated in its own package.
Real-time Tweet Posting: Post tweets directly through the app using the Twitter SDK.
Secure Authentication: OAuth 1.0 integration ensures secure communication with Twitter's API.

![Screenshot_1723509939](https://github.com/user-attachments/assets/ab3337ff-ce26-42d4-8722-41c51a87db50 = 100x100)
