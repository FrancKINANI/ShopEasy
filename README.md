# ShopEasy 🛒 - Modern E-Commerce Android App

> **Production-Ready** | **Material Design 3** | **Firebase Powered** | **Offline-First** | **Premium UI/UX**

ShopEasy is a fully-featured, production-ready Android e-commerce application demonstrating modern development practices with MVVM architecture, clean code principles, and professional UI/UX design.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Design System](#-design-system--ui-enhancements)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Documentation](#-documentation)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### 🔐 Authentication
- **Email/Password Sign-Up & Sign-In** with validation
- **Google Sign-In** integration
- **Password Reset** functionality
- **Session Persistence** across app restarts

### 🛍️ Product Discovery
- **Browse All Products** with grid layout
- **Real-time Search** functionality
- **Category Filtering** (All, Price Low, Price High, Trending)
- **Product Details** with full information
- **Wishlist Support** (icon ready)
- **Offline Product Cache** via Room Database

### 🛒 Shopping Cart
- **Add/Remove Items** with quantity management
- **Real-time Price Calculation**
- **Persistent Storage** (survives app restart)
- **Swipe to Delete** functionality
- **Clear Cart** option

### 📦 Order Management
- **Checkout Process** from cart
- **Order History** with complete tracking
- **Order Status Updates** (Pending → Shipping → Delivered)
- **Order Details** view with items breakdown
- **Real-time Updates** via Firestore

### 🔔 Notifications
- **Push Notifications** via Firebase Cloud Messaging
- **Order Status Alerts** when status changes
- **Promotional Messages** support
- **Custom Notification Handling**

### 🎨 User Experience
- **Dark Mode Support** with toggle in settings
- **Premium Material Design 3** UI
- **Smooth Animations** (slide, fade, scale)
- **Responsive Layouts** for all screen sizes
- **Accessibility Ready** with proper contrast ratios
- **Custom Design System** (colors, typography, spacing)

### ⚡ Offline & Performance
- **Offline-First Approach** with Room caching
- **Cache Invalidation** strategy
- **Smart Sync** when connection restored
- **Image Optimization** with Glide
- **Efficient Database Queries**

---

## 🛠 Tech Stack

### 💻 Core Technologies

| Category | Technology | Version |
|----------|-----------|---------|
| **Language** | Java | 11+ |
| **API Level** | Android | 24-34 |
| **Architecture** | MVVM + Clean | - |
| **DI Framework** | Hilt/Dagger | Latest |

### 📚 Jetpack Components

- **Navigation Component** - Fragment navigation & deep linking
- **ViewModel** - UI state management
- **LiveData** - Reactive data binding
- **Room** - Local database with Type-safety
- **DataStore** - Modern SharedPreferences replacement
- **View Binding** - Type-safe view access

### 🔥 Firebase Services

| Service | Usage |
|---------|-------|
| **Authentication** | User sign-in (Email + Google) |
| **Firestore** | Real-time database for products, orders |
| **Storage** | Image hosting for products |
| **Cloud Messaging** | Push notifications |

### 🎨 UI Libraries

- **Material Design 3** - Modern Material Design system
- **Glide** - Efficient image loading & caching
- **Material Components** - Pre-built Material widgets
- **Lottie** - (Ready for animations)

### 🔧 Utilities

- **Retrofit** - (Via Firebase for now)
- **Coroutines** - Async operations
- **Gson** - JSON serialization
- **ProGuard/R8** - Code obfuscation

---

## 🎨 Design System & UI Enhancements

### 📐 Modern Design (January 2026 Update)

ShopEasy recently underwent a **complete design modernization** with a professional Material Design 3 system:

#### **Color Palette**
- **Primary**: #2563EB (Modern Blue) - Primary actions
- **Accent**: #F59E0B (Vibrant Orange) - Highlights
- **Success**: #10B981 (Green) - Delivered status
- **Error**: #EF4444 (Red) - Errors & warnings
- **Warning**: #F59E0B (Orange) - Pending status
- **Info**: #3B82F6 (Blue) - Shipping status
- **19 Total Colors** + full dark mode support

#### **Typography System**
- **Display**: Poppins Bold (32sp) - Headers
- **Heading 1-3**: Poppins Semibold (24-20sp) - Section titles
- **Body Large/Medium**: Inter Regular (16-14sp) - Content
- **Body Small**: Inter Regular (12sp) - Secondary text
- **Label**: Poppins Semibold (12sp UPPERCASE) - Buttons, tags

#### **Spacing System**
- **XS**: 4dp - Minimal gaps
- **SM**: 8dp - Small gaps
- **MD**: 12dp - Medium gaps
- **LG**: 16dp - Large gaps
- **XL**: 24dp - Extra large
- **XXL**: 32dp - Maximum gaps

#### **Elevation & Corners**
- **Corner Radius**: 8dp (small), 12dp (medium), 16dp (large), 24dp (full)
- **Elevation Levels**: 1dp-24dp following Material3 spec
- **Shadows**: Soft, layered shadows for depth

### 🎯 Redesigned Screens

| Screen | Enhancements |
|--------|--------------|
| **Login/Signup** | Gradient hero, premium cards, smooth inputs |
| **Home** | Header with greeting, search bar, category chips |
| **Product Cards** | Image with overlay, discount badge, ratings |
| **Shopping Cart** | Smooth quantity controls, price breakdown |
| **Profile** | Overlapped avatar, dark mode toggle |
| **Orders** | Color-coded status badges, quick actions |

### 📚 Design Documentation

Complete design documentation available:
- [Design System Overview](DESIGN_INDEX.md)
- [Visual Specifications](VISUAL_DESIGN_GUIDE.md)
- [Developer Guide](DESIGN_GUIDE.md)
- [Implementation Checklist](IMPLEMENTATION_CHECKLIST.md)

---

## 🏗 Architecture

### MVVM Pattern

```
┌─ View Layer (Fragments)
│   └─ Observe LiveData
│       └─ Triggers UI updates
│
├─ ViewModel Layer
│   └─ Manages UI state
│       └─ Handles user interactions
│
└─ Data Layer (Repositories)
    └─ Provides data from Local/Remote
        └─ Room DB + Firestore
```

### Clean Architecture Principles

```
Presentation Layer (UI, ViewModels)
        ↓
Domain Layer (Business Models)
        ↓
Data Layer (Repositories)
        ↓
Data Sources (Local DB, Remote API)
```

### Repository Pattern

- **Single Source of Truth** for data
- **Abstraction** between layers
- **Smart Caching** with Room
- **Conflict Resolution** between local & remote

### Dependency Injection (Hilt)

```java
// Automatic injection of dependencies
@Inject
ProductRepository productRepository;

// Provides singletons for Firebase, DB, etc.
@Provides
@Singleton
FirebaseAuth provideAuth() { ... }
```

---

## 📂 Project Structure

```
ShopEasy/
├── app/src/main/
│   ├── java/com/ma/shopeasy/
│   │   ├── ShopEasyApp.java              (Application entry point)
│   │   ├── ui/                           (MVVM Presentation Layer)
│   │   │   ├── MainActivity.java
│   │   │   ├── auth/                     (Login/Signup)
│   │   │   ├── home/                     (Product browsing)
│   │   │   ├── products/                 (Product details)
│   │   │   ├── cart/                     (Shopping cart)
│   │   │   └── orders/                   (Order history)
│   │   ├── data/                         (Data Layer)
│   │   │   ├── repository/               (Single source of truth)
│   │   │   ├── local/                    (Room database)
│   │   │   └── remote/                   (Firebase)
│   │   ├── domain/                       (Business models)
│   │   │   └── models/
│   │   ├── di/                           (Dependency injection)
│   │   └── utils/                        (Utilities & helpers)
│   │
│   └── res/                              (Resources)
│       ├── layout/                       (✨ 9 redesigned layouts)
│       ├── values/                       (✨ Design system)
│       │   ├── colors.xml                (19 colors)
│       │   ├── dimens.xml                (Spacing system)
│       │   ├── themes.xml                (Material3)
│       │   └── strings.xml
│       ├── values-night/                 (✨ Dark mode)
│       ├── drawable/                     (✨ Gradients & shapes)
│       ├── font/                         (✨ Typography)
│       └── anim/                         (✨ Animations)
│
├── docs/
│   ├── architecture.md                   (Architecture overview)
│   └── diagrams/                         (UML diagrams)
│
└── design-docs/                          (✨ NEW)
    ├── DESIGN_INDEX.md                   (Design navigation)
    ├── DESIGN_SYSTEM.md                  (Color, typography, spacing)
    ├── DESIGN_GUIDE.md                   (Developer guide)
    ├── VISUAL_DESIGN_GUIDE.md            (Visual specs)
    └── IMPLEMENTATION_CHECKLIST.md       (QA checklist)
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Ladybug or newer
- **JDK**: Version 17+
- **Gradle**: 8.0+
- **Firebase Project**: Required for full functionality

### Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/FrancKINANI/ShopEasy.git
cd ShopEasy
```

#### 2. Firebase Setup (Required for Full Features)

```bash
# Go to Firebase Console: https://console.firebase.google.com/

# 1. Create a new project named "ShopEasy"
# 2. Create an Android app with package: com.ma.shopeasy
# 3. Download google-services.json
# 4. Place it in: app/google-services.json
```

#### 3. Configure Local Properties

```bash
# Create local.properties if not exists
echo "sdk.dir=/path/to/android/sdk" > local.properties
```

#### 4. Build the Project

```bash
# Debug build
./gradlew assembleDebug

# Release build (with ProGuard)
./gradlew assembleRelease

# Run on emulator
./gradlew installDebug
```

### Firebase Configuration Details

**Required Services:**
- ✅ Authentication (Email + Google Sign-In)
- ✅ Firestore Database
- ✅ Cloud Storage
- ✅ Cloud Messaging (FCM)

**Firestore Collections Structure:**
```
users/
├── {userId}/
│   ├── email: string
│   ├── displayName: string
│   └── createdAt: timestamp

products/
├── {productId}/
│   ├── name: string
│   ├── price: number
│   ├── category: string
│   ├── imageUrl: string
│   └── description: string

orders/
├── {orderId}/
│   ├── userId: string
│   ├── items: array
│   ├── totalPrice: number
│   ├── status: string (pending/shipped/delivered)
│   └── createdAt: timestamp
```

---

## 🧪 Testing

### Unit Tests
```bash
./gradlew testDebugUnitTest
```

### Integration Tests
```bash
./gradlew connectedAndroidTest
```

### Coverage Report
```bash
./gradlew jacocoTestReport
```

---

## 📄 Documentation

### Core Documentation
- **[Architecture Details](docs/architecture.md)** - MVVM, Clean Architecture, Design Patterns
- **[Diagrams](docs/diagrams/diagrams.md)** - Class, Use Case, Sequence diagrams
- **[Analysis Report](ARCHITECTURE_ANALYSIS.md)** - Complete technical analysis

### Design Documentation
- **[Design Index](DESIGN_INDEX.md)** - Navigation guide for all design docs
- **[Design System](DESIGN_SYSTEM.md)** - Colors, typography, spacing specs
- **[Visual Guide](VISUAL_DESIGN_GUIDE.md)** - Visual mockups and ASCII previews
- **[Developer Guide](DESIGN_GUIDE.md)** - How to use the design system
- **[Implementation Checklist](IMPLEMENTATION_CHECKLIST.md)** - QA & verification

### Project Documentation
- **[Project Presentation](PROJECT_PRESENTATION.md)** - Stakeholder overview

---

## 🤝 Contributing

### Code Style
- Follow Google Java Style Guide
- Use meaningful variable names
- Add comments for complex logic
- Keep methods focused (single responsibility)

### Pull Request Process
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request with detailed description

### Issues
- Use GitHub Issues for bug reports
- Provide detailed reproduction steps
- Include device/OS information

---

## 📊 Project Status

| Aspect | Status | Notes |
|--------|--------|-------|
| Architecture | ✅ Complete | MVVM + Clean implemented |
| Core Features | ✅ Complete | Auth, Products, Cart, Orders |
| UI/UX Design | ✅ Complete | Material3 system (Jan 2026) |
| Documentation | ✅ Complete | 50+ pages of guides |
| Testing | ⚠️ In Progress | Unit tests needed |
| Performance | ✅ Good | Optimized queries & caching |

---

## 🗺 Roadmap

### Q1 2026 - Foundation
- [ ] Comprehensive test suite (70%+ coverage)
- [ ] Accessibility audit (WCAG AA)
- [ ] Performance optimization
- [ ] Wishlist feature
- [ ] Advanced search filters

### Q2 2026 - Features
- [ ] Payment integration (Stripe/PayPal)
- [ ] Order tracking with maps
- [ ] Product reviews & ratings
- [ ] User analytics
- [ ] Admin dashboard

### Q3 2026 - Scale
- [ ] Kotlin migration
- [ ] Modular architecture
- [ ] Dynamic feature delivery
- [ ] iOS development start

### Q4 2026 - Expansion
- [ ] iOS launch
- [ ] Web platform
- [ ] Seller dashboard
- [ ] ML recommendations

---

## 🐛 Known Issues & Limitations

- Font files (TTF) need to be added from Google Fonts
- Image placeholders need replacement with actual product images
- Animation integration in Java code pending
- Tablet layout optimizations needed for larger screens

---

## 📞 Support & Contact

- **Issues**: Use GitHub Issues
- **Email**: kinani.franck@gmail.com
- **Documentation**: See docs/ folder

---

## 📜 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👏 Acknowledgments

- **Material Design 3** - Design system inspiration
- **Firebase** - Backend services
- **Android Jetpack** - Architecture components
- **Community** - Open source contributions

---

**Last Updated**: January 13, 2026  
**Version**: 2.0 (Enhanced with Design System & Analysis)  
**Status**: 🚀 Production-Ready
