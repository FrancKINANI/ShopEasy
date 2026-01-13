# ShopEasy - Architecture & Flow Diagrams

**Document Purpose**: Comprehensive visual documentation of ShopEasy's architecture, design patterns, and user flows.

---

## 📊 1. System Architecture Diagram

```mermaid
graph TB
    subgraph "Presentation Layer"
        MainActivity["MainActivity"]
        Fragments["Fragments<br/>(Auth, Home, Cart, Orders, Profile)"]
        ViewModels["ViewModels<br/>(AuthVM, HomeVM, CartVM, etc)"]
    end

    subgraph "Domain Layer"
        Models["Business Models<br/>(User, Product, Order, CartItem)"]
    end

    subgraph "Data Layer"
        Repositories["Repositories<br/>(Auth, Product, Cart, Order)"]
    end

    subgraph "Local Data Source"
        RoomDB["Room Database<br/>(Products, Cart, Orders)"]
        DataStore["DataStore<br/>(Preferences, Theme)"]
    end

    subgraph "Remote Data Source"
        Firebase["Firebase Services<br/>(Auth, Firestore, Storage, FCM)"]
    end

    MainActivity --> Fragments
    Fragments --> ViewModels
    ViewModels --> Models
    ViewModels --> Repositories
    Repositories --> RoomDB
    Repositories --> DataStore
    Repositories --> Firebase
    
    style MainActivity fill:#2563EB,color:#fff
    style Fragments fill:#3B82F6,color:#fff
    style ViewModels fill:#60A5FA,color:#fff
    style Models fill:#10B981,color:#fff
    style Repositories fill:#F59E0B,color:#fff
    style RoomDB fill:#8B5CF6,color:#fff
    style DataStore fill:#8B5CF6,color:#fff
    style Firebase fill:#EF4444,color:#fff
```

---

## 🎭 2. Use Case Diagram

```mermaid
usecaseDiagram
    actor Guest
    actor "Authenticated User" as AuthUser
    actor "Admin" as Admin
    actor "Firebase" as FB
    actor "Payment Gateway" as Payment

    package "ShopEasy Application" {
        %% Authentication Use Cases
        usecase "Sign Up" as UC_SignUp
        usecase "Login" as UC_Login
        usecase "Logout" as UC_Logout
        usecase "Reset Password" as UC_ResetPwd
        usecase "Google Sign-In" as UC_GoogleAuth
        
        %% Product Management
        usecase "Browse Products" as UC_Browse
        usecase "Search Products" as UC_Search
        usecase "Filter by Category" as UC_Filter
        usecase "View Product Details" as UC_Details
        usecase "Add to Wishlist" as UC_Wishlist
        
        %% Cart Operations
        usecase "Add to Cart" as UC_AddCart
        usecase "Remove from Cart" as UC_RemCart
        usecase "Update Quantity" as UC_UpdateQty
        usecase "Clear Cart" as UC_ClearCart
        
        %% Checkout & Orders
        usecase "Proceed to Checkout" as UC_Checkout
        usecase "Process Payment" as UC_Payment
        usecase "Create Order" as UC_CreateOrder
        usecase "View Order History" as UC_OrderHistory
        usecase "Track Order" as UC_TrackOrder
        usecase "Cancel Order" as UC_CancelOrder
        
        %% User Profile
        usecase "View Profile" as UC_Profile
        usecase "Edit Profile" as UC_EditProfile
        usecase "Toggle Dark Mode" as UC_DarkMode
        usecase "Manage Notifications" as UC_Notifications
        
        %% System Features
        usecase "Receive Notifications" as UC_ReceiveNotif
    }

    %% Guest User flows
    Guest --> UC_Browse
    Guest --> UC_Search
    Guest --> UC_Filter
    Guest --> UC_Details
    Guest --> UC_SignUp
    Guest --> UC_Login
    Guest --> UC_GoogleAuth

    %% Authenticated User flows
    AuthUser --> UC_Browse
    AuthUser --> UC_Search
    AuthUser --> UC_Filter
    AuthUser --> UC_Details
    AuthUser --> UC_AddCart
    AuthUser --> UC_RemCart
    AuthUser --> UC_UpdateQty
    AuthUser --> UC_ClearCart
    AuthUser --> UC_Wishlist
    AuthUser --> UC_Checkout
    AuthUser --> UC_CreateOrder
    AuthUser --> UC_OrderHistory
    AuthUser --> UC_TrackOrder
    AuthUser --> UC_CancelOrder
    AuthUser --> UC_Profile
    AuthUser --> UC_EditProfile
    AuthUser --> UC_DarkMode
    AuthUser --> UC_Notifications
    AuthUser --> UC_Logout

    %% External System interactions
    UC_SignUp --> FB
    UC_Login --> FB
    UC_GoogleAuth --> FB
    UC_ResetPwd --> FB
    UC_Payment --> Payment
    UC_ReceiveNotif --> FB
```

---

## 🏗️ 3. Class Diagram (MVVM Architecture)

```mermaid
classDiagram
    %% UI Layer
    class MainActivity {
        -NavController navController
        -ActivityMainBinding binding
        +onCreate()
    }

    class BaseFragment {
        <<abstract>>
        #binding: ViewBinding
        #onCreate()
        #onDestroy()
    }

    %% Auth Layer
    class LoginFragment {
        -AuthViewModel viewModel
        +validateEmail()
        +validatePassword()
    }

    class SignupFragment {
        -AuthViewModel viewModel
        +validateInputs()
    }

    class AuthViewModel {
        -AuthRepository authRepository
        +loginLiveData: LiveData~Resource~
        +signupLiveData: LiveData~Resource~
        +login()
        +signup()
        +resetPassword()
    }

    %% Home Layer
    class HomeFragment {
        -HomeViewModel viewModel
        -ProductAdapter adapter
        -SearchView searchView
        +initViews()
        +setupRecyclerView()
    }

    class HomeViewModel {
        -ProductRepository repository
        +productsLiveData: LiveData~List~Product~~
        +filteredProductsLiveData: LiveData~List~Product~~
        +fetchProducts()
        +searchProducts()
        +filterByCategory()
    }

    class ProductDetailFragment {
        -ProductDetailViewModel viewModel
        +displayProductInfo()
        +addToCart()
    }

    class ProductDetailViewModel {
        -ProductRepository repository
        -CartRepository cartRepository
        +productLiveData: LiveData~Product~
        +getProductDetails()
        +addToCart()
        +addToWishlist()
    }

    %% Cart Layer
    class CartFragment {
        -CartViewModel viewModel
        -CartAdapter adapter
        +updateCart()
        +showCheckout()
    }

    class CartViewModel {
        -CartRepository cartRepository
        -OrderRepository orderRepository
        +cartItemsLiveData: LiveData~List~CartItem~~
        +totalPriceLiveData: LiveData~Double~
        +getCartItems()
        +removeItem()
        +updateQuantity()
        +placeOrder()
    }

    %% Orders Layer
    class OrdersFragment {
        -OrdersViewModel viewModel
        -OrderAdapter adapter
    }

    class OrdersViewModel {
        -OrderRepository repository
        +ordersLiveData: LiveData~List~Order~~
        +getOrders()
        +cancelOrder()
        +trackOrder()
    }

    %% Profile Layer
    class ProfileFragment {
        -ProfileViewModel viewModel
        +displayUserInfo()
        +toggleDarkMode()
    }

    class ProfileViewModel {
        -AuthRepository authRepository
        -PreferencesRepository prefRepository
        +userLiveData: LiveData~User~
        +darkModeLiveData: LiveData~Boolean~
        +logout()
        +updateProfile()
        +toggleDarkMode()
    }

    %% Repository Layer
    class AuthRepository {
        -FirebaseAuth firebaseAuth
        +login()
        +signup()
        +logout()
        +getCurrentUser()
    }

    class ProductRepository {
        -FirebaseFirestore firestore
        -ProductDao productDao
        +fetchProducts()
        +searchProducts()
        +getProductById()
        +cacheProducts()
    }

    class CartRepository {
        -CartDao cartDao
        +addToCart()
        +removeFromCart()
        +getCartItems()
        +clearCart()
        +updateQuantity()
    }

    class OrderRepository {
        -FirebaseFirestore firestore
        -OrderDao orderDao
        +placeOrder()
        +getOrders()
        +cancelOrder()
        +syncOrders()
    }

    %% Data Access Layer
    class AppDatabase {
        <<abstract>>
        +productDao()
        +cartDao()
        +orderDao()
    }

    class ProductDao {
        <<interface>>
        +insertAll()*
        +getAll()*
        +getById()*
        +deleteAll()*
    }

    class CartDao {
        <<interface>>
        +insert()*
        +delete()*
        +getAll()*
        +update()*
    }

    class OrderDao {
        <<interface>>
        +insert()*
        +getAll()*
        +getById()*
        +update()*
    }

    %% Models
    class User {
        +String uid
        +String email
        +String displayName
        +String photoUrl
        +Long createdAt
    }

    class Product {
        +String id
        +String name
        +String description
        +Double price
        +Double originalPrice
        +String imageUrl
        +String category
        +Float rating
        +Integer reviewCount
    }

    class CartItem {
        +String productId
        +String productName
        +Double price
        +Integer quantity
        +String imageUrl
    }

    class Order {
        +String id
        +String userId
        +List~CartItem~ items
        +Double totalPrice
        +String status
        +Long createdAt
        +String trackingNumber
    }

    %% Relationships
    MainActivity --> HomeFragment
    MainActivity --> LoginFragment
    MainActivity --> CartFragment
    MainActivity --> OrdersFragment
    MainActivity --> ProfileFragment

    LoginFragment --|> BaseFragment
    SignupFragment --|> BaseFragment
    HomeFragment --|> BaseFragment
    ProductDetailFragment --|> BaseFragment
    CartFragment --|> BaseFragment
    OrdersFragment --|> BaseFragment
    ProfileFragment --|> BaseFragment

    LoginFragment --> AuthViewModel
    SignupFragment --> AuthViewModel
    HomeFragment --> HomeViewModel
    ProductDetailFragment --> ProductDetailViewModel
    CartFragment --> CartViewModel
    OrdersFragment --> OrdersViewModel
    ProfileFragment --> ProfileViewModel

    AuthViewModel --> AuthRepository
    HomeViewModel --> ProductRepository
    ProductDetailViewModel --> ProductRepository
    ProductDetailViewModel --> CartRepository
    CartViewModel --> CartRepository
    CartViewModel --> OrderRepository
    OrdersViewModel --> OrderRepository
    ProfileViewModel --> AuthRepository

    AuthRepository --> User
    ProductRepository --> Product
    ProductRepository --> ProductDao
    CartRepository --> CartItem
    CartRepository --> CartDao
    OrderRepository --> Order
    OrderRepository --> OrderDao

    AppDatabase --> ProductDao
    AppDatabase --> CartDao
    AppDatabase --> OrderDao
```

---

## 🔄 4. Data Flow Diagram (Product Browsing)

```mermaid
graph LR
    User["👤 User<br/>(Browse Products)"]
    
    HF["HomeFragment"]
    HVM["HomeViewModel"]
    PR["ProductRepository"]
    
    subgraph "Local Cache"
        RDB["Room Database<br/>(productDao)"]
    end
    
    subgraph "Remote Source"
        FS["Firestore<br/>(products collection)"]
    end
    
    User -->|"1. Observe"| HF
    HF -->|"2. Bind"| HVM
    HVM -->|"3. Request"| PR
    PR -->|"4. Check Cache"| RDB
    RDB -->|"5a. Return Cached"| PR
    PR -->|"6. Return (if cached)"| HVM
    
    PR -->|"5b. Fetch Remote"| FS
    FS -->|"7. Return Data"| PR
    PR -->|"8. Cache Locally"| RDB
    PR -->|"9. Update LiveData"| HVM
    HVM -->|"10. Observe Change"| HF
    HF -->|"11. Display Products"| User
```

---

## 🔐 5. Authentication Flow (Sequence Diagram)

```mermaid
sequenceDiagram
    participant User
    participant App as "LoginFragment"
    participant VM as "AuthViewModel"
    participant Repo as "AuthRepository"
    participant FB as "Firebase Auth"
    participant FS as "Firestore"

    User->>App: Enter credentials & click Login
    App->>VM: login(email, password)
    
    VM->>Repo: login(email, password)
    Repo->>FB: signInWithEmailAndPassword()
    
    alt Authentication Successful
        FB-->>Repo: AuthResult + User
        Repo->>FS: Get user document
        FS-->>Repo: User data
        Repo->>Repo: Cache user session
        Repo-->>VM: Resource.Success(user)
        VM-->>App: updateUI(user)
        App->>App: Navigate to Home
        App-->>User: Show Home Screen
    else Authentication Failed
        FB-->>Repo: FirebaseAuthException
        Repo-->>VM: Resource.Error(exception)
        VM-->>App: updateUI(error)
        App-->>User: Show Error Message
    end
```

---

## 🛒 6. Shopping Cart & Checkout Flow (Sequence Diagram)

```mermaid
sequenceDiagram
    participant User
    participant CF as "CartFragment"
    participant CVM as "CartViewModel"
    participant CR as "CartRepository"
    participant OR as "OrderRepository"
    participant RDB as "Room DB"
    participant FS as "Firestore"

    User->>CF: Click Add to Cart
    CF->>CVM: addToCart(productId)
    CVM->>CR: addToCart(cartItem)
    CR->>RDB: Insert cartItem
    RDB-->>CR: Success
    CR-->>CVM: cartItemsUpdated
    CVM-->>CF: Update LiveData
    CF-->>User: Show Toast (Added)

    User->>CF: Review cart items
    CF->>CVM: observeCartItems()
    CVM->>CR: getCartItems()
    CR->>RDB: Query all items
    RDB-->>CR: List~CartItem~
    CR-->>CVM: cartItemsLiveData.emit()
    CVM-->>CF: Display items

    User->>CF: Click Checkout
    CF->>CVM: placeOrder(cartItems)
    CVM->>OR: placeOrder(order)
    
    OR->>RDB: Create order record
    RDB-->>OR: OrderId
    OR->>FS: Save order to Firestore
    FS-->>OR: DocumentReference
    
    OR->>CR: clearCart()
    CR->>RDB: Delete all cart items
    RDB-->>CR: Success
    
    OR-->>CVM: Resource.Success(orderId)
    CVM-->>CF: Navigation to Orders
    CF-->>User: Show Confirmation
```

---

## 🎯 7. State Management Diagram (User Login States)

```mermaid
stateDiagram-v2
    [*] --> Idle
    
    Idle --> Loading: User clicks Login
    Loading --> Success: Auth successful
    Loading --> Error: Auth failed
    
    Success --> Idle: Logout
    Error --> Idle: Retry
    
    Idle --> [*]
    
    note right of Idle
        Not authenticated
        No request pending
    end note
    
    note right of Loading
        Authentication in progress
        Show loading indicator
    end note
    
    note right of Success
        User authenticated
        Cache session
        Navigate to Home
    end note
    
    note right of Error
        Show error message
        User can retry
    end note
```

---

## 📱 8. Screen Navigation Flow

```mermaid
graph TB
    Start["App Start"]
    Auth{Authenticated?}
    
    subgraph "Unauthenticated"
        Login["Login Screen"]
        Signup["Signup Screen"]
        LoginDetails["Forgot Password"]
    end
    
    subgraph "Authenticated"
        Home["Home Screen"]
        ProductDetail["Product Details"]
        Cart["Shopping Cart"]
        Orders["Orders History"]
        Profile["User Profile"]
    end
    
    Start --> Auth
    Auth -->|No| Login
    Auth -->|Yes| Home
    
    Login -->|Sign Up| Signup
    Login -->|Forgot Password| LoginDetails
    Login -->|Success| Home
    Signup -->|Back| Login
    LoginDetails -->|Back| Login
    
    Home --> ProductDetail
    ProductDetail -->|Add to Cart| Cart
    ProductDetail -->|Back| Home
    Home --> Cart
    Cart -->|Checkout| Orders
    Cart -->|Continue Shopping| Home
    
    Home --> Profile
    Profile -->|Edit| Profile
    Profile -->|Logout| Login
    
    Orders -->|Back| Home
    Orders -->|Track| Orders
```

---

## 💾 9. Database Schema Diagram

```mermaid
erDiagram
    USER ||--o{ ORDER : places
    USER ||--o{ CART_ITEM : manages
    PRODUCT ||--o{ CART_ITEM : "added to"
    PRODUCT ||--o{ ORDER_ITEM : contains
    ORDER ||--o{ ORDER_ITEM : includes

    USER {
        string uid PK
        string email UK
        string displayName
        string photoUrl
        timestamp createdAt
        timestamp updatedAt
    }

    PRODUCT {
        string id PK
        string name
        string description
        double price
        double originalPrice
        string imageUrl
        string category
        float rating
        int reviewCount
        int inventory
        timestamp createdAt
    }

    CART_ITEM {
        string id PK
        string userId FK
        string productId FK
        int quantity
        double price
        timestamp addedAt
    }

    ORDER {
        string id PK
        string userId FK
        double totalPrice
        string status
        string trackingNumber
        timestamp createdAt
        timestamp updatedAt
    }

    ORDER_ITEM {
        string id PK
        string orderId FK
        string productId FK
        int quantity
        double priceAtPurchase
    }
```

---

## 🔄 10. Sync & Offline Strategy Diagram

```mermaid
graph TB
    CheckConnection{Network<br/>Available?}
    
    Offline["📴 Offline Mode"]
    Online["📡 Online Mode"]
    
    subgraph "Offline Operations"
        OL1["Read from<br/>Room Cache"]
        OL2["Store changes<br/>locally"]
        OL3["Queue pending<br/>operations"]
    end
    
    subgraph "Online Operations"
        OL4["Sync queued<br/>operations"]
        OL5["Fetch latest<br/>from Firestore"]
        OL6["Update Room<br/>Cache"]
    end
    
    CheckConnection -->|Yes| Online
    CheckConnection -->|No| Offline
    
    Offline --> OL1
    OL1 --> OL2
    OL2 --> OL3
    OL3 -->|Connection<br/>Restored| Online
    
    Online --> OL4
    OL4 --> OL5
    OL5 --> OL6
    OL6 -->|Changes<br/>Synced| Online
```

---

## 🎨 11. Dark Mode Theme Architecture

```mermaid
graph LR
    App["ShopEasy App"]
    DataStore["DataStore"]
    Theme{Dark Mode<br/>Enabled?}
    
    subgraph "Light Theme"
        LT1["Primary: #2563EB"]
        LT2["Background: #FFFFFF"]
        LT3["Text: #000000"]
    end
    
    subgraph "Dark Theme"
        DT1["Primary: #2563EB"]
        DT2["Background: #1F2937"]
        DT3["Text: #FFFFFF"]
    end
    
    App -->|Read<br/>Preference| DataStore
    DataStore --> Theme
    Theme -->|Light| LT1
    Theme -->|Dark| DT1
    
    LT1 --> LT2
    LT2 --> LT3
    DT1 --> DT2
    DT2 --> DT3
    
    LT3 -->|Apply Theme| App
    DT3 -->|Apply Theme| App
```

---

## 📊 Summary

This documentation provides:

- **System Architecture**: Overall app structure
- **Use Cases**: All user interactions and system features
- **Class Relationships**: MVVM implementation and dependency structure
- **Data Flow**: How data moves through the application
- **Authentication**: Secure login process
- **Shopping**: Complete purchase workflow
- **State Management**: UI state transitions
- **Navigation**: Screen flow and transitions
- **Database**: Entity relationships and schema
- **Sync Strategy**: Offline support mechanism
- **Theme**: Dark mode implementation

All diagrams follow modern Android architecture best practices with separation of concerns, reactive programming, and offline-first approach.

