# Amazon (CLI E‑Commerce Simulator)

A console-based, command-driven e-commerce simulator written in plain Java, inspired by Amazon. It models two kinds of accounts — **Customers** and **Stores** — and supports account management, product listing and browsing, shopping carts, checkout with saved credit cards and addresses, ratings/reviews, and store-side inventory, pricing, discounts, and profit tracking.

There is no GUI — every action is triggered by typing a specific command string into the terminal, similar to a shell.

## Features

### Accounts
- Create a customer account or a store (brand) account
- Log in / log out as a customer or a store
- Delete the currently logged-in account
- Input validation for names, emails, passwords, and store/brand names via regex

### Customer capabilities
- View and edit personal info (name, email, password)
- Manage shipping addresses (add / delete / list)
- Manage credit cards (add a card, charge it, check its balance)
- Browse products (sort by rating, price, or units sold; paginate 10 at a time)
- View product details and rate/review products
- Add products to cart, view cart, remove items from cart
- Checkout using a saved card and address
- View past orders and order details

### Store capabilities
- Add new products (name, cost, price, description, initial stock)
- Update stock and price for existing products
- Apply discounts to a product for a limited quantity
- View the store's product list
- View total profit (revenue minus costs)

## Architecture

The project follows an **MVC (Model–View–Controller)** structure:

```
Amazon/
├── Main.java              # Entry point — launches the app
├── models/                # Domain objects and application state
│   ├── App.java           # Static in-memory application state (users, session, current menu)
│   ├── User.java          # Base class for accounts
│   ├── Costumer.java      # Customer account (orders, addresses, cards, cart)
│   ├── Store.java         # Store/brand account (products, revenue, costs)
│   ├── Product.java       # Product entity (price, discount, stock, ratings)
│   ├── Order.java         # A placed order
│   ├── Address.java       # A customer's shipping address
│   ├── Card.java          # A saved credit card
│   ├── Rating.java        # A product review/rating
│   ├── Result.java        # Generic success/failure result wrapper
│   └── enums/              # Menus and regex-based command definitions
│       ├── Menu.java
│       ├── Command.java
│       ├── UserType.java
│       ├── MainMenuCommands.java
│       ├── LoginMenuCommands.java
│       ├── CostumerMenuCommands.java
│       ├── StoreMenuCommands.java
│       └── ProductMenuCommands.java
├── views/                 # Menus that read user input and dispatch to controllers
│   ├── AppMenu.java / AppView.java
│   ├── MainMenu.java
│   ├── LoginMenu.java
│   ├── CostumerMenu.java
│   ├── StoreMenu.java
│   ├── ProductMenu.java
│   └── ExitMenu.java
└── controllers/           # Business logic for each menu
    ├── LoginMenuController.java
    ├── CostumerMenuController.java
    ├── StoreMenuController.java
    └── ProductMenuController.java
```

- **Models** hold data and simple domain logic (e.g. computing a discounted price or an average rating). `App` acts as an in-memory "database" and session holder — there is no persistent storage; all data resets when the program exits.
- **Views** read a line of input, match it against the current menu's commands, and forward matched groups to a controller.
- **Controllers** validate input and mutate the models, returning a `Result` that the view uses to print feedback to the user.
- Commands are defined as **regular expressions** in enums (implementing a common `Command` interface), which makes the CLI grammar easy to see and extend in one place per menu.

## Requirements

- JDK 11 or later (no external dependencies, no build tool required)

## Running the project

From the project root:

```bash
# Compile all sources into an "out" directory
javac -d out $(find . -name "*.java")

# Run the app
java -cp out Main
```

Or simply open the folder in an IDE such as IntelliJ IDEA / Eclipse, mark it as a Java project, and run `Main.java`.

## Usage

The app starts in the **Main Menu**. Commands are typed as plain text; each menu only accepts its own set of commands. A few examples:

**Main Menu**
```
go to -m LoginMenu
exit
```

**Login Menu**
```
create a user account -fn John -ln Doe -p Passw0rd -rp Passw0rd -e john.doe@example.com
login as user -e john.doe@example.com -p Passw0rd
create a store account -b "My Shop" -p Passw0rd -rp Passw0rd -e shop@example.com
login as store -e shop@example.com -p Passw0rd
go back
```

**Customer Menu** (after logging in as a user)
```
show my info
add address -country USA -city NYC -street "5th Ave" -postal 1234567890
add a credit card -number 1234567812345678 -ed 09/28 -cvv 123 -initialValue 500
list my orders
go back
```

**Product Menu**
```
show products -sortBy rate
show information of -id 101
add to cart -product 101 -quantity 2
checkout -card 1 -address 1
go back
```

**Store Menu** (after logging in as a store)
```
add product -n "Wireless Mouse" -pc 5.5 -p 12.99 -about "Ergonomic wireless mouse" -np 100
apply discount -p 101 -d 20 -q 10
show profit
go back
```

Type `go back` from most sub-menus to return to the previous menu, and `exit` from the Main Menu to quit.


#
