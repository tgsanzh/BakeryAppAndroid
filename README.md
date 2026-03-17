# 🍞 BakeryApp

BakeryApp is an Android e-commerce application for a local bakery.
It allows users to register, sign in, browse products, add items to a cart, place orders, and view order history.

The project is built with **Kotlin**, **Jetpack Compose**, **Ktor**, and **Koin**, and demonstrates a modern Android client connected to a backend API.

---

## Features

* Splash / start screen with session check
* User registration
* User login
* Token-based authentication
* Product catalog
* Category loading
* Product search
* Add products to cart
* Increase and decrease cart item quantity
* Enter delivery address and place an order
* View order history
* Sign out

---

## Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **Navigation Compose**
* **Ktor Client**
* **Kotlinx Serialization**
* **Koin**
* **Coil**
* **Material 3**

---

## Architecture

The project follows a feature-based structure with separated layers for:

* **presentation**
* **data**
* **domain**

Main modules:

```text
app/src/main/java/com/example/bakeryapp/
├── start/
├── login/
├── register/
├── catalog/
├── cart/
├── orders/
├── utils/
├── MainActivity.kt
├── DI.kt
├── Network.kt
└── KoinApp.kt
```

Each feature contains its own:

* `presentation` — screen, state, event, view model
* `data` — repository, DTOs
* `domain` — entities and mappers

The UI is driven by a simple **State + Event** approach using `MutableStateFlow` inside ViewModels.

---

## Screens

* **Start Screen**
* **Login Screen**
* **Register Screen**
* **Catalog Screen**
* **Cart Screen**
* **Orders Screen**

The app also includes a bottom navigation bar for the main authenticated flow:

* Catalog
* Cart
* Orders

---

## Authentication

Authentication is handled through a backend API.

* After login or registration, the app stores an access token in `SharedPreferences`
* The token is attached to requests through Ktor Bearer Auth
* On app launch, the start screen checks whether a token already exists
* If a token is present, the user is redirected to the catalog
* If not, the user is redirected to the login screen

---

## Networking

The app uses **Ktor Client** with:

* `OkHttp` engine
* `ContentNegotiation`
* `Kotlinx Serialization`
* `Logging`
* `Bearer Authentication`

Configured backend base URL:

```text
http://10.0.2.2:8080
```

The code also contains a commented production backend URL for deployment.

### Main API flows

* `POST /users/register`
* `POST /users/login`
* `GET /products/`
* `GET /categories/`
* `POST /cart/`
* `GET /cart/`
* `PUT /cart/{id}/change-quantity`
* `POST /orders/`
* `GET /orders/`

---

## Catalog Flow

The catalog screen supports:

* Loading products from the backend
* Loading categories
* Searching products by text
* Filtering by category
* Adding a product to cart

---

## Cart Flow

The cart screen supports:

* Loading cart items from the backend
* Increasing item quantity
* Decreasing item quantity
* Automatic total price calculation
* Entering delivery address
* Creating an order from cart contents

---

## Orders Flow

The orders screen supports:

* Loading user orders
* Displaying order status
* Showing delivery address
* Showing ordered items and quantities
* Signing out from the account

---

## Dependency Injection

Dependency injection is implemented with **Koin**.

Injected components include:

* `HttpClient`
* `SharedPrefs`
* validation use cases
* repositories
* view models

---

## Validation

The login and registration flow includes simple validation for:

* phone number
* password length
* password confirmation

---

## Build Configuration

* **Min SDK:** 26
* **Target SDK:** 34
* **Compile SDK:** 35

---

## Getting Started

```bash
git clone https://github.com/tgsanzh/BakeryAppAndroid.git
cd BakeryAppAndroid
```

Then open the project in **Android Studio** and run it on an emulator or Android device.

> For local development, make sure the backend server is available at `http://10.0.2.2:8080`.

---

## What This Project Demonstrates

* Building a real client app for a backend service
* Working with token-based authentication
* Organizing code by feature
* Using Ktor in Android
* Managing UI state with ViewModels and StateFlow
* Implementing a basic e-commerce flow in Jetpack Compose

---

## Future Improvements

* Better error handling
* Loading and empty states for all screens
* Product details screen
* Better form validation
* Dependency inversion for network configuration
* Unit tests
* UI tests
* Persistent local cart cache

---

## Author

**Sanzhar Tursynbay**
