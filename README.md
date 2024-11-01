# Android Chat Application

## Overview

The React Chat Application is a simple android-based chat platform built using Android and NodeJS. It provides users with the ability to register an account, log in, and engage in real-time conversations with other users.

## Features

- User Registration: Users can create an account by providing a username, password, and display name.
- User Login: Registered users can log in to access the chat interface.
- Real-time Chat: Users can send and receive messages in real-time with other logged-in users.
- Private Routing: Certain routes are protected and can only be accessed by authenticated users.
- Theme Choosing

## Prerequisites

- Node.js
- npm
- MongoDB
- Android Studio with Emulator / Android Device

## Installation

1. Clone the repository: `git clone https://github.com/asaf27064/ex3New.git`
2. Navigate to the project directory: `cd ex3New`
3. Install server dependencies: `cd server` then `npm install`


## Usage

1. Start the server:
   - Navigate to the server directory: `cd server`
   - Start the server: `npm start`
2. Open the project in Android Studio.
3. Ensure that you have the necessary Android SDK and dependencies installed.
4. Connect an Android device or start an emulator.
5. Build and run the project from Android Studio.
6. The application should install and launch on the connected device or emulator.
7. Follow the on-screen instructions to navigate through the application's features, such as logging in, registering, adding contacts, and chatting.

## Project Structure

The project follows a specific directory structure to organize the server and client components:


## File Descriptions

Here is a brief explanation of each file and directory:

- `AddContactActivity.java`: Represents an activity where users can add new contacts to their contact list.

- `ChatScreenActivity.java`: Represents an activity where users can chat with other contacts.

- `ContactsActivity.java`: Represents an activity that displays a list of contacts.

- `LogInScreenActivity.java`: Represents an activity for user login.

- `MyApplication.java`: Represents the custom application class for your Android application.

- `RegisterScreenActivity.java`: Represents an activity for user registration.

- `SettingActivity.java`: Represents an activity where users can configure settings for the application.

### Adapters

- `ChatAdapter.java`: An adapter for displaying chat messages.
- `ContactsListAdapter.java`: An adapter for displaying a list of contacts.

### API

- `ChatsAPI.java`: API interface for handling chats functionality.
- `LoginCredentials.java`: Represents login credentials for API authentication.
- `PostAPI.java`: API interface for handling post requests.
- `RegistrationAPI.java`: API interface for handling user registration.
- `TokenAPI.java`: API interface for managing authentication tokens.
- `UserAPI.java`: API interface for user-related operations.
- `WebServiceAPI.java`: API interface for general web service requests.

### DB

- `ChatsDB.java`: Database helper class or entity for storing chats.
- `ContactsDB.java`: Database helper class or entity for storing contacts.
- `MessagesDB.java`: Database helper class or entity for storing messages.

### Entities

- `Chat.java`: Represents the structure of a chat object.
- `Contact.java`: Represents the structure of a contact object.
- `Message.java`: Represents the structure of a message object.

### Model

- `NewChatRequest.java`: Model class for creating a new chat request.
- `RegistrationRequest.java`: Model class for user registration request.
- `RegistrationResponse.java`: Model class for user registration response.
- `SendMessageRequest.java`: Model class for sending a message request.
- `UserResponse.java`: Model class for user response.

### Repositories

- `ChatsRepository.java`: Repository class for handling chat-related operations.
- `ContactsRepository.java`: Repository class for handling contact-related operations.
- `MessagesRepository.java`: Repository class for handling message-related operations.

### Rooms

- `ChatsDao.java`: Data Access Object (DAO) for chat-related database operations.
- `ContactsDao.java`: Data Access Object (DAO) for contact-related database operations.
- `MessagesDao.java`: Data Access Object (DAO) for message-related database operations.
- `MessageTypeConverter.java`: Type converter for message objects in the database.
- `UserResponseTypeConverter.java`: Type converter for user response objects in the database.

### View Models

- `ChatsViewModel.java`: ViewModel class for providing data to chat-related UI components.
- `ContactsViewModel.java`: ViewModel class for providing data to contact-related UI components.
- `MessagesViewModel.java`: ViewModel class for providing data to message-related UI components.

### Server
- `server`: Contains the server-side code and configurations.
  - `controllers`: Contains the logic for handling API requests and business logic.
  - `models`: Contains the database models and schemas used by the server.
  - `public`: Contains static assets that will be served by the server.
    - `build`: Contains the production build of the client application.
      - `static`: Contains static assets bundled by the client build process.
  - `routes`: Contains the API route definitions and associated middleware.
  - `services`: Contains additional services or utilities used by the server.

## Authors

-Asaf Ohana
-Nadav Barda
-Avigail Danesh
