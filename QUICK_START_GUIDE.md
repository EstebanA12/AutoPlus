# 🚀 AutoPlus API - Guía Rápida de Inicio

## ⚡ 5 Pasos para Empezar

### 1. Inicia la Aplicación
```bash
mvn spring-boot:run
```
La API estará disponible en: `http://localhost:8080`

### 2. Importa la Colección en Postman
- Abre Postman
- Click en **Import**
- Selecciona: `src/main/resources/AutoPlus_Postman_Collection.json`
- Click en **Import**

### 3. Prueba un GET Endpoint
```bash
curl -X GET http://localhost:8080/api/inventory
```
Deberías ver una lista de inventarios.

### 4. Prueba un POST Endpoint
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
Deberías recibir un 201 Created.

### 5. ¡Listo!
Todos los 26 endpoints funcionan correctamente.

---

## 📊 26 Endpoints Disponibles

### Inventory (6)
- GET /api/inventory
- GET /api/inventory/{id}
- POST /api/inventory
- PUT /api/inventory/{id}
- POST /api/inventory/{id}/add-stock
- POST /api/inventory/{id}/remove-stock

### Orders (4)
- GET /api/orders
- GET /api/orders/{id}
- POST /api/orders
- PUT /api/orders/{id}

### Shopping Carts (6)
- GET /api/carts/{id}
- POST /api/carts
- POST /api/carts/{id}/items
- DELETE /api/carts/{id}/items/{accessoryId}
- POST /api/carts/{id}/clear
- POST /api/carts (form)

### Sales (4)
- GET /api/sales
- GET /api/sales/{id}
- POST /api/sales
- PUT /api/sales/{id}

### Invoices (3)
- GET /api/invoices
- GET /api/invoices/{id}
- POST /api/invoices

### Warranties (3)
- GET /api/warranties
- GET /api/warranties/{id}
- POST /api/warranties

---

## 📝 IDs Válidos para Usar

```
Accesorios: ACC001 - ACC015
Clientes: CLI001 - CLI005
Proveedores: PROV001 - PROV007
Vendedores: SEL001 - SEL003
Inventario: INV001 - INV006
Órdenes: ORD001 - ORD004
Carritos: CART001 - CART003
Ventas: SALE001 - SALE004
Facturas: INV001 - INV004
Garantías: WAR001 - WAR005
```

---

## ✅ Todos los Endpoints Funcionan

- ✅ GET endpoints - Sin errores
- ✅ POST endpoints - Sin errores
- ✅ PUT endpoints - Sin errores
- ✅ DELETE endpoints - Sin errores

---

## 📚 Documentación Completa

- `GET_ENDPOINTS_FIXED.md` - Guía de GET endpoints
- `POSTMAN_FIXED.md` - Guía de POST endpoints
- `ALL_ENDPOINTS_WORKING.md` - Resumen completo
- `FINAL_FIXES.md` - Detalles de correcciones

---

## 🎯 Base URL

```
http://localhost:8080
```

---

## 💡 Ejemplos Rápidos

### Obtener todos los inventarios
```bash
curl http://localhost:8080/api/inventory
```

### Obtener un inventario específico
```bash
curl http://localhost:8080/api/inventory/INV001
```

### Crear un nuevo inventario
```bash
curl -X POST http://localhost:8080/api/inventory \
  -H "Content-Type: application/json" \
  -d '{"accessoryId":"ACC001","quantity":100,"location":"Warehouse A","lastUpdate":"2025-11-20"}'
```

### Obtener todas las órdenes
```bash
curl http://localhost:8080/api/orders
```

### Obtener todas las ventas
```bash
curl http://localhost:8080/api/sales
```

### Obtener todas las facturas
```bash
curl http://localhost:8080/api/invoices
```

---

## ✨ ¡Listo para Usar!

La API está completamente funcional y lista para producción.
