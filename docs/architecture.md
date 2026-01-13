# ShopEasy Architecture Documentation

## Overview
ShopEasy follows a clean architecture approach using the **MVVM (Model-View-ViewModel)** pattern. It leverages Google's **Android Architecture Components (AAC)** and **Firebase** as a backend service.

## Layers

### 1. Presentation Layer (UI & ViewModel)
- **Activities/Fragments**: Responsibile for UI rendering and observing data changes. Uses **View Binding** for safe view access.
- **ViewModels**: Holds UI-related data and survives configuration changes. Communicates with the Repository layer.
- **Navigation Component**: Manages screen flows and argument passing using a centralized `nav_graph.xml`.

### 2. Domain Layer (Models)
- **Models**: Plain Java objects (POJOs) representing business entities (`User`, `Product`, `Order`, `CartItem`).
- **Use Cases (Optional)**: In this project, logic is largely kept in Repositories for simplicity, but can be extracted into Use Cases for complex business rules.

### 3. Data Layer (Repositories & Data Sources)
- **Repositories**: The single source of truth for the app. It decides whether to fetch data from the remote (Firebase) or local (Room) cache.
- **Local Data Source (Room)**: Provides offline persistence for products and user settings.
- **Remote Data Source (Firebase)**:
    - **Firestore**: Real-time document database.
    - **Auth**: User session management.
    - **Storage**: Image storage for products.
    - **Cloud Messaging (FCM)**: Push notifications.

## Dependency Injection
**Hilt** is used to provide dependencies across the app. Modules are defined in the `di` package to provide Singleton instances of Firebase and Room components.

## Data Flow
1. User interacts with a `Fragment`.
2. `Fragment` calls a method on `ViewModel`.
3. `ViewModel` requests data from `Repository`.
4. `Repository` returns a `LiveData<Resource<T>>` (Loading, Success, Error).
5. `Fragment` observes the `LiveData` and updates the UI accordingly.
