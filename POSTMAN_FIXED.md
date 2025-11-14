# ✅ AutoPlus Postman Collection - TODOS LOS POST CORREGIDOS

## 🔧 Problemas Corregidos

### Problema 1: Accesorios en archivos separados
- ❌ Antes: `accessories.csv` (no existía)
- ✅ Ahora: 
  - `technological_accessories.csv` (ACC001-ACC005)
  - `interior_accessories.csv` (ACC006-ACC010)
  - `exterior_accessories.csv` (ACC011-ACC015)

### Problema 2: Proveedores en archivos separados
- ❌ Antes: `providers.csv` (no existía)
- ✅ Ahora:
  - `local_providers.csv` (PROV001-PROV004)
  - `international_providers.csv` (PROV005-PROV007)

### Problema 3: Archivos de relaciones faltantes
- ✅ Creado: `cart_items.csv` (items en carritos)
- ✅ Creado: `sale_items.csv` (items en ventas)

---

## 🚀 Cómo Usar Ahora

### 1. Inicia la aplicación
```bash
mvn spring-boot:run
```

### 2. Importa la colección en Postman
- Archivo: `src/main/resources/AutoPlus_Postman_Collection.json`

### 3. Prueba los endpoints

---

## ✅ Endpoints POST - Todos Funcionan

### Inventory - POST Create
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
**Respuesta esperada:** 201 Created

---

### Inventory - PUT Update
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
**Respuesta esperada:** 200 OK

---

### Orders - POST Create
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
**Respuesta esperada:** 201 Created

---

### Orders - PUT Update
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
**Respuesta esperada:** 200 OK

---

### Shopping Carts - POST Create
```bash
curl -X POST http://localhost:8080/api/carts \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "CLI001",
    "creationDate": "2025-11-20"
  }'
```
**Respuesta esperada:** 201 Created

---

### Shopping Carts - POST Add Item
```bash
curl -X POST "http://localhost:8080/api/carts/CART001/items?accessoryId=ACC001&quantity=2" \
  -H "Content-Type: application/x-www-form-urlencoded"
```
**Respuesta esperada:** 204 No Content

---

### Shopping Carts - DELETE Remove Item
```bash
curl -X DELETE http://localhost:8080/api/carts/CART001/items/ACC001 \
  -H "Content-Type: application/json"
```
**Respuesta esperada:** 204 No Content

---

### Shopping Carts - POST Clear
```bash
curl -X POST http://localhost:8080/api/carts/CART001/clear \
  -H "Content-Type: application/json"
```
**Respuesta esperada:** 204 No Content

---

### Sales - POST Create
```bash
curl -X POST http://localhost:8080/api/sales \
  -H "Content-Type: application/json" \
  -d '{
    "client": {
      "id": "CLI001",
      "name": "Juan Pérez",
      "email": "juan.perez@email.com",
      "phone": "555-0001",
      "address": "Calle 1 #123",
      "totalSpent": 0.0
    },
    "seller": {
      "id": "SEL001",
      "name": "John Manager",
      "email": "john@autoplus.com",
      "salary": 3000.00,
      "department": "Sales",
      "salesCommission": 0.0,
      "salesCount": 0
    },
    "items": [
      {
        "accessory": {
          "id": "ACC001",
          "name": "Tire Michelin",
          "description": "Durable all-season tire",
          "price": 89.99,
          "stock": 100,
          "discountPercentage": 0.0
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
**Respuesta esperada:** 201 Created

---

### Sales - PUT Update
```bash
curl -X PUT http://localhost:8080/api/sales/SALE001 \
  -H "Content-Type: application/json" \
  -d '{
    "client": {
      "id": "CLI001",
      "name": "Juan Pérez",
      "email": "juan.perez@email.com",
      "phone": "555-0001",
      "address": "Calle 1 #123",
      "totalSpent": 0.0
    },
    "seller": {
      "id": "SEL001",
      "name": "John Manager",
      "email": "john@autoplus.com",
      "salary": 3000.00,
      "department": "Sales",
      "salesCommission": 0.0,
      "salesCount": 0
    },
    "items": [
      {
        "accessory": {
          "id": "ACC001",
          "name": "Tire Michelin",
          "description": "Durable all-season tire",
          "price": 89.99,
          "stock": 100,
          "discountPercentage": 0.0
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
**Respuesta esperada:** 200 OK

---

### Invoices - POST Create
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
        "phone": "555-0001",
        "address": "Calle 1 #123",
        "totalSpent": 0.0
      },
      "seller": {
        "id": "SEL001",
        "name": "John Manager",
        "email": "john@autoplus.com",
        "salary": 3000.00,
        "department": "Sales",
        "salesCommission": 0.0,
        "salesCount": 0
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
**Respuesta esperada:** 201 Created

---

### Warranties - POST Create
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
**Respuesta esperada:** 201 Created

---

## 📊 Datos Válidos

### Accesorios (Todos funcionan)
- ACC001 a ACC015

### Clientes
- CLI001 a CLI005

### Proveedores (Todos funcionan)
- PROV001 a PROV007

### Vendedores
- SEL001 a SEL003

### Inventario
- INV001 a INV006

### Órdenes
- ORD001 a ORD004

### Carritos
- CART001 a CART003

### Ventas
- SALE001 a SALE004

### Facturas
- INV001 a INV004

### Garantías
- WAR001 a WAR005

---

## 📁 Archivos CSV Creados/Actualizados

```
data/csv/
├── technological_accessories.csv  ✅ (ACC001-ACC005)
├── interior_accessories.csv       ✅ (ACC006-ACC010)
├── exterior_accessories.csv       ✅ (ACC011-ACC015)
├── local_providers.csv            ✅ (PROV001-PROV004)
├── international_providers.csv    ✅ (PROV005-PROV007)
├── clients.csv                    ✅ (CLI001-CLI005)
├── sellers.csv                    ✅ (SEL001-SEL003)
├── inventory.csv                  ✅ (INV001-INV006)
├── orders.csv                     ✅ (ORD001-ORD004)
├── shopping_carts.csv             ✅ (CART001-CART003)
├── cart_items.csv                 ✅ (items en carritos)
├── sales.csv                      ✅ (SALE001-SALE004)
├── sale_items.csv                 ✅ (items en ventas)
├── invoices.csv                   ✅ (INV001-INV004)
└── warranties.csv                 ✅ (WAR001-WAR005)
```

---

## ✨ Status: ✅ TODOS LOS POST FUNCIONAN

- ✅ Accesorios en archivos correctos
- ✅ Proveedores en archivos correctos
- ✅ Datos de relaciones completos
- ✅ Estructura de objetos correcta
- ✅ Todos los POST sin errores
- ✅ Listo para usar
