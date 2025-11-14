# 🔧 Solución Error 500 - POST /api/sales

## 🔴 Problema

El POST /api/sales da error 500 porque:

1. El SaleItem requiere un accesorio válido
2. El accesorio debe existir en los archivos de accesorios
3. Si el accesorio no existe, falla la creación

## ✅ Solución

### Paso 1: Verifica que los accesorios existan

Los accesorios deben estar en uno de estos archivos:
- `data/csv/technological_accessories.csv` (ACC001-ACC005)
- `data/csv/interior_accessories.csv` (ACC006-ACC010)
- `data/csv/exterior_accessories.csv` (ACC011-ACC015)

### Paso 2: Usa un accesorio válido en el POST

**Accesorio válido:**
```json
{
  "id": "ACC001",
  "name": "Tire Michelin",
  "description": "Durable all-season tire",
  "price": 89.99,
  "stock": 100,
  "discountPercentage": 0.0
}
```

### Paso 3: POST /api/sales correcto

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

## 📊 Datos Válidos

### Clientes (Válidos)
- CLI001: Juan Pérez
- CLI002: María García
- CLI003: Carlos López
- CLI004: Ana Martínez
- CLI005: Roberto Sánchez

### Vendedores (Válidos)
- SEL001: John Manager
- SEL002: Maria Sales
- SEL003: Carlos Vendor

### Accesorios (Válidos)
- ACC001-ACC005: technological_accessories.csv
- ACC006-ACC010: interior_accessories.csv
- ACC011-ACC015: exterior_accessories.csv

## 🚀 Cómo Resolver

1. **Reinicia la aplicación:**
   ```bash
   mvn clean spring-boot:run
   ```

2. **Prueba el POST en Postman:**
   - Usa los datos válidos de arriba
   - Asegúrate de que el accesorio existe
   - Verifica que cliente y vendedor existan

3. **Si aún falla:**
   - Revisa los logs de la aplicación
   - Verifica que los CSV no estén corruptos
   - Intenta con ACC001 que siempre debe existir

## ✅ Checklist

- [x] Clientes existen (CLI001-CLI005)
- [x] Vendedores existen (SEL001-SEL003)
- [x] Accesorios existen (ACC001-ACC015)
- [x] CSV limpios sin null
- [x] Estructura correcta

## 🎯 Status

✅ **Datos válidos**
✅ **Estructura correcta**
✅ **POST /api/sales debería funcionar**
