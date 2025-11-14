# AutoPlus Postman Collection - Import Guide

## 📋 Overview
This guide explains how to import and use the AutoPlus API collection in Postman.

## 📁 Collection File Location
```
src/main/resources/AutoPlus_Postman_Collection.json
```

## 🚀 Quick Start

### Step 1: Start the Application
```bash
mvn spring-boot:run
```
The API will be available at: `http://localhost:8080`

### Step 2: Import Collection in Postman

1. Open **Postman**
2. Click **Import** button (top-left)
3. Select **Upload Files**
4. Navigate to: `src/main/resources/AutoPlus_Postman_Collection.json`
5. Click **Import**

## 📊 API Endpoints Summary

### Inventory (6 endpoints)
- **GET** `/api/inventory` - Get all inventory items
- **GET** `/api/inventory/{id}` - Get inventory by ID (e.g., INV001)
- **POST** `/api/inventory` - Create new inventory
- **PUT** `/api/inventory/{id}` - Update inventory
- **POST** `/api/inventory/{id}/add-stock` - Add stock quantity
- **POST** `/api/inventory/{id}/remove-stock` - Remove stock quantity

### Orders (4 endpoints)
- **GET** `/api/orders` - Get all orders
- **GET** `/api/orders/{id}` - Get order by ID (e.g., ORD001)
- **POST** `/api/orders` - Create new order
- **PUT** `/api/orders/{id}` - Update order

### Shopping Carts (6 endpoints)
- **GET** `/api/carts/{id}` - Get cart by ID (e.g., CART001)
- **POST** `/api/carts` - Create cart (JSON)
- **POST** `/api/carts` - Create cart (Form)
- **POST** `/api/carts/{id}/items` - Add item to cart
- **DELETE** `/api/carts/{id}/items/{accessoryId}` - Remove item from cart
- **POST** `/api/carts/{id}/clear` - Clear cart

### Sales (4 endpoints)
- **GET** `/api/sales` - Get all sales
- **GET** `/api/sales/{id}` - Get sale by ID (e.g., SALE001)
- **POST** `/api/sales` - Create new sale
- **PUT** `/api/sales/{id}` - Update sale

### Invoices (3 endpoints)
- **GET** `/api/invoices` - Get all invoices
- **GET** `/api/invoices/{id}` - Get invoice by ID (e.g., INV001)
- **POST** `/api/invoices` - Create new invoice

### Warranties (3 endpoints)
- **GET** `/api/warranties` - Get all warranties
- **GET** `/api/warranties/{id}` - Get warranty by ID (e.g., WAR001)
- **POST** `/api/warranties` - Create new warranty

## 📝 Valid Test IDs

### Accessories
- ACC001 through ACC015

### Clients
- CLI001 through CLI005

### Providers
- PROV001 through PROV007

### Sellers
- SEL001 through SEL003

### Inventory
- INV001 through INV006

### Orders
- ORD001 through ORD004

### Shopping Carts
- CART001 through CART003

### Sales
- SALE001 through SALE004

### Invoices
- INV001 through INV004

### Warranties
- WAR001 through WAR005

## 🔧 Configuration

### Base URL
- Default: `http://localhost:8080`
- All endpoints in the collection use this base URL

### Port Configuration
If you need to change the port:
1. Edit `src/main/resources/application.properties`
2. Update `server.port=8080` to your desired port
3. Update all URLs in Postman collection accordingly

## ✅ Testing

All endpoints are pre-configured with:
- ✅ Valid test data
- ✅ Proper headers
- ✅ Example request bodies
- ✅ Valid IDs that exist in the system

### Example: Get Inventory
```
GET http://localhost:8080/api/inventory/INV001
```

### Example: Create Order
```
POST http://localhost:8080/api/orders
Content-Type: application/json

{
  "providerId": "PROV001",
  "accessoryId": "ACC001",
  "quantity": 50,
  "totalCost": 4499.50,
  "orderDate": "2025-11-20",
  "status": "Pending"
}
```

## 📂 Test Data Files

All test data is stored in CSV format:
- `data/csv/accessories.csv` - 15 accessories
- `data/csv/clients.csv` - 5 clients
- `data/csv/providers.csv` - 7 providers
- `data/csv/sellers.csv` - 3 sellers
- `data/csv/inventory.csv` - 6 inventory items
- `data/csv/orders.csv` - 4 orders
- `data/csv/shopping_carts.csv` - 3 shopping carts
- `data/csv/sales.csv` - 4 sales
- `data/csv/invoices.csv` - 4 invoices
- `data/csv/warranties.csv` - 5 warranties

## 🐛 Troubleshooting

### Port Already in Use
If port 8080 is already in use:
1. Change port in `application.properties`
2. Update Postman collection URLs

### 404 Not Found
- Verify the ID exists in the test data
- Check the endpoint path spelling

### 400 Bad Request
- Verify required fields are included in request body
- Check that referenced IDs exist (e.g., accessoryId, clientId)

### Connection Refused
- Ensure the application is running with `mvn spring-boot:run`
- Verify the correct port is configured

## 📞 Support
For issues or questions, refer to the project documentation in the repository.
