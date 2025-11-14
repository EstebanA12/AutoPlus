# AutoPlus API - cURL Examples

All these curl commands work without errors and can be used for testing.

## 🚀 Prerequisites
- Application running: `mvn spring-boot:run`
- Base URL: `http://localhost:8080`

---

## 📦 Inventory Endpoints

### Get All Inventory
```bash
curl -X GET http://localhost:8080/api/inventory \
  -H "Content-Type: application/json"
```

### Get Inventory by ID
```bash
curl -X GET http://localhost:8080/api/inventory/INV001 \
  -H "Content-Type: application/json"
```

### Create Inventory
```bash
curl -X POST http://localhost:8080/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "accessoryId": "ACC001",
    "quantity": 100,
    "location": "Warehouse A",
    "lastUpdate": "2025-11-20"
  }'
```

### Update Inventory
```bash
curl -X PUT http://localhost:8080/api/inventory/INV001 \
  -H "Content-Type: application/json" \
  -d '{
    "accessoryId": "ACC001",
    "quantity": 200,
    "location": "Warehouse B",
    "lastUpdate": "2025-11-20"
  }'
```

### Add Stock
```bash
curl -X POST "http://localhost:8080/api/inventory/INV001/add-stock?amount=50" \
  -H "Content-Type: application/x-www-form-urlencoded"
```

### Remove Stock
```bash
curl -X POST "http://localhost:8080/api/inventory/INV001/remove-stock?amount=25" \
  -H "Content-Type: application/x-www-form-urlencoded"
```

---

## 📋 Orders Endpoints

### Get All Orders
```bash
curl -X GET http://localhost:8080/api/orders \
  -H "Content-Type: application/json"
```

### Get Order by ID
```bash
curl -X GET http://localhost:8080/api/orders/ORD001 \
  -H "Content-Type: application/json"
```

### Create Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "providerId": "PROV001",
    "accessoryId": "ACC001",
    "quantity": 50,
    "totalCost": 4499.50,
    "orderDate": "2025-11-20",
    "status": "Pending"
  }'
```

### Update Order
```bash
curl -X PUT http://localhost:8080/api/orders/ORD001 \
  -H "Content-Type: application/json" \
  -d '{
    "providerId": "PROV001",
    "accessoryId": "ACC001",
    "quantity": 75,
    "totalCost": 6749.25,
    "orderDate": "2025-11-20",
    "status": "In Transit"
  }'
```

---

## 🛒 Shopping Carts Endpoints

### Get Cart by ID
```bash
curl -X GET http://localhost:8080/api/carts/CART001 \
  -H "Content-Type: application/json"
```

### Create Cart (JSON)
```bash
curl -X POST http://localhost:8080/api/carts \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "CLI001",
    "creationDate": "2025-11-20"
  }'
```

### Create Cart (Form)
```bash
curl -X POST http://localhost:8080/api/carts \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "clientId=CLI002"
```

### Add Item to Cart
```bash
curl -X POST "http://localhost:8080/api/carts/CART001/items?accessoryId=ACC001&quantity=2" \
  -H "Content-Type: application/x-www-form-urlencoded"
```

### Remove Item from Cart
```bash
curl -X DELETE http://localhost:8080/api/carts/CART001/items/ACC001 \
  -H "Content-Type: application/json"
```

### Clear Cart
```bash
curl -X POST http://localhost:8080/api/carts/CART001/clear \
  -H "Content-Type: application/json"
```

---

## 💰 Sales Endpoints

### Get All Sales
```bash
curl -X GET http://localhost:8080/api/sales \
  -H "Content-Type: application/json"
```

### Get Sale by ID
```bash
curl -X GET http://localhost:8080/api/sales/SALE001 \
  -H "Content-Type: application/json"
```

### Create Sale
```bash
curl -X POST http://localhost:8080/api/sales \
  -H "Content-Type: application/json" \
  -d '{
    "client": {
      "id": "CLI001",
      "name": "Juan Pérez",
      "email": "juan.perez@email.com",
      "phone": "555-0001"
    },
    "seller": {
      "id": "SEL001",
      "name": "AutoPlus Main Store",
      "location": "Bogotá"
    },
    "items": [
      {
        "accessory": {
          "id": "ACC001",
          "name": "Tire Michelin",
          "price": 89.99
        },
        "quantity": 2,
        "unitPrice": 89.99
      }
    ],
    "totalAmount": 179.98,
    "saleDate": "2025-11-20",
    "status": "Completed"
  }'
```

### Update Sale
```bash
curl -X PUT http://localhost:8080/api/sales/SALE001 \
  -H "Content-Type: application/json" \
  -d '{
    "client": {
      "id": "CLI001",
      "name": "Juan Pérez",
      "email": "juan.perez@email.com",
      "phone": "555-0001"
    },
    "seller": {
      "id": "SEL001",
      "name": "AutoPlus Main Store",
      "location": "Bogotá"
    },
    "items": [
      {
        "accessory": {
          "id": "ACC001",
          "name": "Tire Michelin",
          "price": 89.99
        },
        "quantity": 3,
        "unitPrice": 89.99
      }
    ],
    "totalAmount": 269.97,
    "saleDate": "2025-11-20",
    "status": "Completed"
  }'
```

---

## 📄 Invoices Endpoints

### Get All Invoices
```bash
curl -X GET http://localhost:8080/api/invoices \
  -H "Content-Type: application/json"
```

### Get Invoice by ID
```bash
curl -X GET http://localhost:8080/api/invoices/INV001 \
  -H "Content-Type: application/json"
```

### Create Invoice
```bash
curl -X POST http://localhost:8080/api/invoices \
  -H "Content-Type: application/json" \
  -d '{
    "sale": {
      "id": "SALE001",
      "client": {
        "id": "CLI001",
        "name": "Juan Pérez",
        "email": "juan.perez@email.com",
        "phone": "555-0001"
      },
      "seller": {
        "id": "SEL001",
        "name": "AutoPlus Main Store",
        "location": "Bogotá"
      },
      "items": [],
      "totalAmount": 179.98,
      "saleDate": "2025-11-20",
      "status": "Completed"
    },
    "subtotal": 179.98,
    "tax": 0.0,
    "total": 179.98,
    "invoiceDate": "2025-11-20"
  }'
```

---

## 🛡️ Warranties Endpoints

### Get All Warranties
```bash
curl -X GET http://localhost:8080/api/warranties \
  -H "Content-Type: application/json"
```

### Get Warranty by ID
```bash
curl -X GET http://localhost:8080/api/warranties/WAR001 \
  -H "Content-Type: application/json"
```

### Create Warranty
```bash
curl -X POST http://localhost:8080/api/warranties \
  -H "Content-Type: application/json" \
  -d '{
    "accessoryId": "ACC001",
    "months": 24,
    "description": "Full coverage tire warranty",
    "startDate": "2025-11-20",
    "endDate": "2027-11-20"
  }'
```

---

## 📊 Valid Test IDs

Use these IDs in your requests:

| Type | IDs |
|------|-----|
| Accessories | ACC001 - ACC015 |
| Clients | CLI001 - CLI005 |
| Providers | PROV001 - PROV007 |
| Sellers | SEL001 - SEL003 |
| Inventory | INV001 - INV006 |
| Orders | ORD001 - ORD004 |
| Shopping Carts | CART001 - CART003 |
| Sales | SALE001 - SALE004 |
| Invoices | INV001 - INV004 |
| Warranties | WAR001 - WAR005 |

---

## 🔍 Testing Tips

1. **Test GET endpoints first** - They don't modify data
2. **Use valid IDs** - All IDs in the table above exist in the system
3. **Check headers** - Always include `Content-Type: application/json` for JSON requests
4. **Verify responses** - All endpoints return proper HTTP status codes
5. **Copy-paste ready** - All curl commands are ready to use

---

## ✅ All Endpoints Verified

- ✅ No 400 Bad Request errors
- ✅ No 404 Not Found errors
- ✅ No 500 Server errors
- ✅ All IDs pre-populated and valid
- ✅ All relationships correct
- ✅ Ready for production testing
