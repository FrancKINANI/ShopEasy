# ShopEasy 🛒

ShopEasy is a production-ready Android E-commerce application built with **Java**, following the **MVVM** architecture, and powered by **Firebase**.

## ✨ Features

- **Authentication**: Email/Password and Google Sign-In.
- **Product Discovery**: Browse by category, search with real-time suggestions, and advanced filtering.
- **Shopping Cart**: Fully functional cart with quantity management.
- **Order History**: Track your orders from pending to delivered.
- **Theme Support**: Dynamic Dark/Light mode using **Jetpack DataStore**.
- **Offline Support**: Products cached locally via **Room Database**.
- **Push Notifications**: Receive updates via **Firebase Cloud Messaging**.

## 🛠 Tech Stack

- **Architecture**: MVVM + Clean Architecture principles.
- **DI**: Hilt (Dagger).
- **Jetpack**: Navigation, ViewModel, LiveData, Room, DataStore.
- **Backend**: Firebase Auth, Firestore, Storage, FCM.
- **UI**: Material Design 3, Glide, View Binding.

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or newer.
- JDK 17.
- A Firebase Project.

### Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/FrancKINANI/ShopEasy.git
   cd ShopEasy
   ```

2. **Firebase Configuration**:
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project named "ShopEasy".
   - Add an Android App with package name `com.ma.shopeasy`.
   - Download `google-services.json` and place it in the `app/` directory.

3. **Placeholder Google Services**:
   If you just want to see the code structure, there is a `google-services.json.example` in the root.

4. **Build the project**:
   ```bash
   ./gradlew assembleDebug
   ```

## 📂 Project Structure

- `ui/`: Fragments and ViewModels organized by feature.
- `data/`: Repositories and DataSources (Local & Remote).
- `domain/`: Business logic models.
- `di/`: Hilt Dependency Injection modules.
- `docs/`: Detailed architecture docs and diagrams.

## 📄 Documentation
- [Architecture Details](docs/architecture.md)
- [Diagrams (Class, Use Case, Sequence)](docs/diagrams/diagrams.md)

## ⚖️ License
This project is licensed under the MIT License - see the LICENSE file for details.
