# 🧪 AutoPlus API - Endpoint Testing Guide

## ⚠️ IMPORTANTE: Estructura de Datos Corregida

Los endpoints ahora usan la estructura correcta de objetos anidados.

---

## ✅ Endpoints que Funcionan SIN PROBLEMAS

### 1. GET Endpoints (Todos funcionan)
Estos endpoints NO requieren cambios:

```bash
# Get All Inventory
curl -X GET http://localhost:8080/api/inventory

# Get Inventory by ID
curl -X GET http://localhost:8080/api/inventory/INV001

# Get All Orders
curl -X GET http://localhost:8080/api/orders

# Get All Sales
curl -X GET http://localhost:8080/api/sales

# Get All Invoices
curl -X GET http://localhost:8080/api/invoices

# Get All Warranties
curl -X GET http://localhost:8080/api/warranties

# Get All Carts
curl -X GET http://localhost:8080/api/carts/CART001
```

---

## 🔧 Endpoints que Requieren Estructura Correcta

### 2. Inventory - POST Create
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

### 3. Inventory - PUT Update
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

### 4. Orders - POST Create
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

### 5. Shopping Cart - POST Create
```bash
curl -X POST http://localhost:8080/api/carts \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "CLI001",
    "creationDate": "2025-11-20"
  }'
```

### 6. Shopping Cart - Add Item
```bash
curl -X POST "http://localhost:8080/api/carts/CART001/items?accessoryId=ACC001&quantity=2" \
  -H "Content-Type: application/x-www-form-urlencoded"
```

### 7. Sales - POST Create (REQUIERE ESTRUCTURA COMPLETA)
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

### 8. Invoices - POST Create (REQUIERE ESTRUCTURA COMPLETA)
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

### 9. Warranties - POST Create
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

## 📝 Datos Válidos para Usar

### Accesorios
- ACC001 a ACC015

### Clientes
- CLI001 a CLI005

### Proveedores
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

## 🚀 Cómo Probar

1. **Inicia la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

2. **Prueba un endpoint GET primero:**
   ```bash
   curl -X GET http://localhost:8080/api/inventory
   ```

3. **Si funciona, prueba un POST:**
   ```bash
   curl -X POST http://localhost:8080/api/inventory \
     -H "Content-Type: application/json" \
     -d '{"accessoryId": "ACC001", "quantity": 100, "location": "Warehouse A", "lastUpdate": "2025-11-20"}'
   ```

4. **Verifica la respuesta:**
   - ✅ 200 OK = Éxito
   - ❌ 400 Bad Request = Datos incorrectos
   - ❌ 404 Not Found = ID no existe
   - ❌ 500 Server Error = Error en el servidor

---

## 🔍 Troubleshooting

### Error 400 Bad Request
- Verifica que todos los campos requeridos estén presentes
- Verifica que los IDs referenciados existan
- Verifica el formato JSON

### Error 404 Not Found
- Verifica que el ID existe en los CSV
- Usa IDs válidos de la lista anterior

### Error 500 Server Error
- Revisa los logs de la aplicación
- Verifica que los datos CSV estén bien formados
- Reinicia la aplicación

---

## ✨ Estado Actual

✅ Todos los endpoints están configurados
✅ Todos los datos están en los CSV
✅ Estructura de objetos corregida
✅ Listo para pruebas
