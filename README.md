# BLU Store - Full-Stack E-Commerce Backend

This is the backend server for the BLU Store, a complete e-commerce web application built from scratch. It is developed using Java and the Spring Boot framework, providing a comprehensive RESTful API to manage products, users, shopping carts, and orders.

## ✨ Features

- **Product Management:** Full CRUD (Create, Read, Update, Delete) capabilities for products, with dynamic filtering by category.
- **Secure User Authentication:** User registration and login system with secure password hashing using Spring Security.
- **Persistent, User-Specific Shopping Carts:** Each logged-in user has their own private shopping cart stored in the database, with full quantity control (add, increment, decrement).
- **Complete Order Processing:** A full checkout workflow that takes a user's cart and address to create a permanent order record in the database.
- **Order History & Management:** API endpoints for viewing a user's order history and canceling recent orders.

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA (Hibernate)
- **Database:** PostgreSQL
- **Build Tool:** Maven

## 🔌 API Endpoints

Here is a list of the main API endpoints available:

### Authentication (`/api/auth`)
| Method | Endpoint             | Description                |
|--------|----------------------|----------------------------|
| `POST` | `/register`          | Register a new user.       |
| `POST` | `/login`             | Log in an existing user.   |

### Products (`/api/products`)
| Method | Endpoint             | Description                |
|--------|----------------------|----------------------------|
| `GET`    | `/`                  | Get a list of all products.|
| `POST`   | `/`                  | Add a new product.         |
| `GET`    | `/category/{category}` | Get products by category.  |
| `GET`    | `/search?query={name}` | Search for products by name.|

### Shopping Cart (`/api/cart`)
| Method | Endpoint                       | Description                            |
|--------|--------------------------------|----------------------------------------|
| `GET`    | `/{username}`                  | Get the cart for a specific user.      |
| `POST`   | `/add/{username}/{productId}`  | Add a product or increment its quantity.|
| `POST`   | `/decrement/{username}/{productId}` | Decrement a product's quantity.      |

### Orders (`/api/orders`)
| Method | Endpoint             | Description                |
|--------|----------------------|----------------------------|
| `POST`   | `/{username}`        | Place a new order.         |
| `GET`    | `/{username}`        | Get a user's order history.|
| `POST`   | `/cancel/{orderId}`  | Cancel an existing order.  |


## 🚀 Setup and Installation

To run this project locally, follow these steps:

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/your-username/blu-store-backend.git](https://github.com/your-username/blu-store-backend.git)
    cd blu-store-backend
    ```

2.  **Setup PostgreSQL Database:**
    - Install PostgreSQL and pgAdmin 4.
    - Open pgAdmin and create a new, empty database named `tshirt_store_db`.

3.  **Configure Application Properties:**
    - Go to `src/main/resources/application.properties`.
    - Update the database connection details with your PostgreSQL username and password:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/tshirt_store_db
    spring.datasource.username=postgres
    spring.datasource.password=YOUR_PASSWORD_HERE
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    ```

4.  **Run the Application:**
    - Open the project in IntelliJ IDEA.
    - The IDE will resolve all the Maven dependencies.
    - Run the `TshirtStoreApplication.java` file.
    - The server will start on `http://localhost:8080`.
