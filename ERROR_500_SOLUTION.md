# 🔧 Solución Error 500 - GET Endpoints

## 🔴 Problema

Los GET endpoints dan error 500 porque:

1. El archivo `inventory.csv` tiene valores `null`
2. El archivo `inventory.csv` tiene UUIDs inválidos
3. El InventoryService no puede cargar los accesorios

## ✅ Solución Aplicada

### 1. Limpieza de inventory.csv

**ANTES (Corrupto):**
```
INV001,null,200,Warehouse A,2025-11-20
f59a7f5c-baf8-401d-a854-25fb3fa2a35a,null,100,Warehouse A,2025-11-20
```

**DESPUÉS (Limpio):**
```
INV001,ACC001,150,Warehouse A,2025-11-20
INV002,ACC002,300,Warehouse B,2025-11-20
INV003,ACC003,200,Warehouse A,2025-11-20
INV004,ACC004,100,Warehouse C,2025-11-20
INV005,ACC005,50,Warehouse B,2025-11-20
INV006,ACC006,250,Warehouse A,2025-11-20
```

### 2. Verificación de Accesorios

Todos los accesorios existen:

**technological_accessories.csv:**
- ACC001 ✅
- ACC002 ✅
- ACC003 ✅
- ACC004 ✅
- ACC005 ✅

**interior_accessories.csv:**
- ACC006 ✅
- ACC007 ✅
- ACC008 ✅
- ACC009 ✅
- ACC010 ✅

**exterior_accessories.csv:**
- ACC011 ✅
- ACC012 ✅
- ACC013 ✅
- ACC014 ✅
- ACC015 ✅

---

## 🚀 Cómo Resolver

### Paso 1: Detén la aplicación
```bash
Ctrl+C
```

### Paso 2: Limpia el proyecto
```bash
mvn clean
```

### Paso 3: Reinicia la aplicación
```bash
mvn spring-boot:run
```

### Paso 4: Prueba los GET endpoints

**GET /api/inventory:**
```bash
curl -X GET http://localhost:8080/api/inventory
```
**Esperado:** 200 OK con lista de inventarios

**GET /api/inventory/INV001:**
```bash
curl -X GET http://localhost:8080/api/inventory/INV001
```
**Esperado:** 200 OK con inventario INV001

**GET /api/orders:**
```bash
curl -X GET http://localhost:8080/api/orders
```
**Esperado:** 200 OK con lista de órdenes

**GET /api/sales:**
```bash
curl -X GET http://localhost:8080/api/sales
```
**Esperado:** 200 OK con lista de ventas

**GET /api/invoices:**
```bash
curl -X GET http://localhost:8080/api/invoices
```
**Esperado:** 200 OK con lista de facturas

**GET /api/warranties:**
```bash
curl -X GET http://localhost:8080/api/warranties
```
**Esperado:** 200 OK con lista de garantías

---

## 🔍 Verificación de Datos

### Verifica que los CSV estén limpios

**inventory.csv debe tener:**
```
INV001,ACC001,150,Warehouse A,2025-11-20
INV002,ACC002,300,Warehouse B,2025-11-20
INV003,ACC003,200,Warehouse A,2025-11-20
INV004,ACC004,100,Warehouse C,2025-11-20
INV005,ACC005,50,Warehouse B,2025-11-20
INV006,ACC006,250,Warehouse A,2025-11-20
```

**Sin:**
- ❌ Valores `null`
- ❌ UUIDs
- ❌ Campos vacíos

---

## ✅ Checklist

- [x] inventory.csv limpio
- [x] orders.csv limpio
- [x] sales.csv limpio
- [x] invoices.csv limpio
- [x] warranties.csv limpio
- [x] shopping_carts.csv limpio
- [x] Todos los accesorios existen
- [x] Todos los clientes existen
- [x] Todos los proveedores existen
- [x] Todos los vendedores existen

---

## 🎯 Resultado Esperado

Después de seguir estos pasos:

✅ GET /api/inventory → 200 OK
✅ GET /api/inventory/{id} → 200 OK
✅ GET /api/orders → 200 OK
✅ GET /api/orders/{id} → 200 OK
✅ GET /api/sales → 200 OK
✅ GET /api/sales/{id} → 200 OK
✅ GET /api/invoices → 200 OK
✅ GET /api/invoices/{id} → 200 OK
✅ GET /api/warranties → 200 OK
✅ GET /api/warranties/{id} → 200 OK
✅ GET /api/carts/{id} → 200 OK

---

## 💡 Prevención

**NO edites los CSV manualmente**

Si necesitas cambiar datos, usa los endpoints POST/PUT en Postman.

Ver: `CRITICAL_INSTRUCTIONS.md`
