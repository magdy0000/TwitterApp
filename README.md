<div align="center">
<img src="https://github.com/user-attachments/assets/ab3337ff-ce26-42d4-8722-41c51a87db50" alt="Screenshot_1723509939" width="300"/>
</div>

Tweet Character Counter

- Description

This project provides a UI component designed to count characters in a tweet, ensuring it adheres to Twitter's character limit. It also integrates with the Twitter SDK/API to post tweets directly from the app.

- Requirements
  Create a UI component that can accurately count user input compared to Twitter's character limit.
  Integrate with Twitter SDK/API to actually post that tweet.
  
- Technologies Used

Architecture: MVI (Model-View-Intent)
Dependency Injection: Dagger Hilt
Design Pattern: Clean Architecture
Asynchronous Programming: Kotlin Coroutines and Flows
UI Binding: ViewBinding
Testing: Unit Tests
Authentication: OAuth 1.0 for handling Twitter authentication

- Features
- 
Character Counting: Accurately counts characters considering Twitter's specific rules.
Modular Design: The UI component is reusable and encapsulated in its own package.
Real-time Tweet Posting: Post tweets directly through the app using the Twitter SDK.
Secure Authentication: OAuth 1.0 integration ensures secure communication with Twitter's API.



