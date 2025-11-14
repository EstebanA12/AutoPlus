# ✅ POST /api/sales - Formato Correcto

## 🔴 Error 400

Error 400 significa que el servidor rechaza la solicitud por datos inválidos o mal formados.

## ✅ Formato Correcto

### Estructura Completa

```json
{
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
        "discountPercentage": 0.0,
        "type": "TECHNOLOGICAL"
      },
      "quantity": 2,
      "unitPrice": 89.99
    }
  ],
  "totalAmount": 179.98,
  "saleDate": "2025-11-20",
  "status": "Completed"
}
```

## 🔍 Checklist de Validación

- [x] `client` es un objeto (no null)
- [x] `client.id` existe (CLI001-CLI005)
- [x] `seller` es un objeto (no null)
- [x] `seller.id` existe (SEL001-SEL003)
- [x] `items` es un array (no vacío)
- [x] `items[0].accessory` es un objeto (no null)
- [x] `items[0].accessory.id` existe (ACC001-ACC015)
- [x] `items[0].accessory.type` es válido (TECHNOLOGICAL, INTERIOR, EXTERIOR)
- [x] `items[0].quantity` es > 0
- [x] `items[0].unitPrice` es > 0
- [x] `totalAmount` es > 0
- [x] `saleDate` es válido (YYYY-MM-DD)
- [x] `status` es válido (Completed, Pending, Cancelled)

## 🚀 Pasos para Probar

### 1. Reinicia la aplicación
```bash
mvn clean spring-boot:run
```

### 2. En Postman

**URL:** `POST http://localhost:8080/api/sales`

**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
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
        "discountPercentage": 0.0,
        "type": "TECHNOLOGICAL"
      },
      "quantity": 2,
      "unitPrice": 89.99
    }
  ],
  "totalAmount": 179.98,
  "saleDate": "2025-11-20",
  "status": "Completed"
}
```

### 3. Respuesta Esperada

**Status:** 201 Created

**Body:**
```json
{
  "id": "uuid-generado",
  "client": { ... },
  "seller": { ... },
  "items": [ ... ],
  "totalAmount": 179.98,
  "saleDate": "2025-11-20",
  "status": "Completed"
}
```

## 📊 Ejemplos Válidos

### Ejemplo 1: Accesorio Tecnológico
```json
{
  "accessory": {
    "id": "ACC001",
    "name": "Tire Michelin",
    "description": "Durable all-season tire",
    "price": 89.99,
    "stock": 100,
    "discountPercentage": 0.0,
    "type": "TECHNOLOGICAL"
  },
  "quantity": 2,
  "unitPrice": 89.99
}
```

### Ejemplo 2: Accesorio Interior
```json
{
  "accessory": {
    "id": "ACC006",
    "name": "Wiper Blade Valeo",
    "description": "Aerodynamic wiper blade",
    "price": 18.50,
    "stock": 120,
    "discountPercentage": 0.0,
    "type": "INTERIOR"
  },
  "quantity": 1,
  "unitPrice": 18.50
}
```

### Ejemplo 3: Accesorio Exterior
```json
{
  "accessory": {
    "id": "ACC011",
    "name": "Power Steering Fluid",
    "description": "Synthetic power steering fluid",
    "price": 16.00,
    "stock": 75,
    "discountPercentage": 0.0,
    "type": "EXTERIOR"
  },
  "quantity": 1,
  "unitPrice": 16.00
}
```

## ✅ Status

✅ **Formato correcto**
✅ **Validación completa**
✅ **POST /api/sales debería funcionar**

## 🎯 Si Aún Hay Error 400

1. Verifica que el JSON sea válido (usa jsonlint.com)
2. Verifica que todos los campos requeridos estén presentes
3. Verifica que los IDs existan en los CSV
4. Verifica que el tipo de accesorio sea válido
5. Revisa los logs de la aplicación
