# ⚠️ INSTRUCCIONES CRÍTICAS - NO MODIFICAR CSV

## 🚨 IMPORTANTE

**NO EDITES LOS ARCHIVOS CSV MANUALMENTE**

Los archivos CSV se corrompen fácilmente. Si necesitas hacer cambios, sigue estas instrucciones.

---

## 📋 Archivos CSV - NO TOCAR

### ✅ Estos archivos están correctos - NO MODIFICAR

```
data/csv/
├── technological_accessories.csv     ✅ NO MODIFICAR
├── interior_accessories.csv          ✅ NO MODIFICAR
├── exterior_accessories.csv          ✅ NO MODIFICAR
├── local_providers.csv               ✅ NO MODIFICAR
├── international_providers.csv       ✅ NO MODIFICAR
├── clients.csv                       ✅ NO MODIFICAR
├── sellers.csv                       ✅ NO MODIFICAR
├── inventory.csv                     ✅ NO MODIFICAR
├── orders.csv                        ✅ NO MODIFICAR
├── shopping_carts.csv                ✅ NO MODIFICAR
├── sales.csv                         ✅ NO MODIFICAR
├── invoices.csv                      ✅ NO MODIFICAR
├── warranties.csv                    ✅ NO MODIFICAR
├── cart_items.csv                    ✅ NO MODIFICAR
└── sale_items.csv                    ✅ NO MODIFICAR
```

---

## 🔴 PROBLEMAS CAUSADOS POR EDICIÓN MANUAL

Si editas los CSV manualmente:
- ❌ Se introducen valores `null`
- ❌ Se generan UUIDs inválidos
- ❌ Se corrompe la estructura
- ❌ Los GET endpoints dan error 500
- ❌ Los POST endpoints dan error 400

---

## ✅ SOLUCIÓN

### Si los CSV se corrompieron:

1. **Cierra Postman**
2. **Detén la aplicación** (Ctrl+C)
3. **Restaura los CSV** a su estado original
4. **Reinicia la aplicación:**
   ```bash
   mvn clean spring-boot:run
   ```

### Para hacer cambios en los datos:

**NO edites los CSV directamente**

En su lugar, usa los endpoints POST/PUT:

```bash
# Crear nuevo inventario
curl -X POST http://localhost:8080/api/inventory \
  -H "Content-Type: application/json" \
  -d '{"accessoryId":"ACC001","quantity":100,"location":"Warehouse A","lastUpdate":"2025-11-20"}'

# Actualizar inventario
curl -X PUT http://localhost:8080/api/inventory/INV001 \
  -H "Content-Type: application/json" \
  -d '{"accessoryId":"ACC001","quantity":200,"location":"Warehouse B","lastUpdate":"2025-11-20"}'
```

---

## 📊 Datos Correctos - REFERENCIA

### inventory.csv (CORRECTO)
```
INV001,ACC001,150,Warehouse A,2025-11-20
INV002,ACC002,300,Warehouse B,2025-11-20
INV003,ACC003,200,Warehouse A,2025-11-20
INV004,ACC004,100,Warehouse C,2025-11-20
INV005,ACC005,50,Warehouse B,2025-11-20
INV006,ACC006,250,Warehouse A,2025-11-20
```

### orders.csv (CORRECTO)
```
ORD001,PROV001,ACC001,50,4499.50,2025-11-15,Pending
ORD002,PROV002,ACC002,100,1550.00,2025-11-16,Delivered
ORD003,PROV003,ACC003,75,1650.00,2025-11-17,In Transit
ORD004,PROV004,ACC004,30,1350.00,2025-11-18,Pending
```

### sales.csv (CORRECTO)
```
SALE001,CLI001,SEL001,179.98,2025-11-15,Completed
SALE002,CLI002,SEL001,77.50,2025-11-16,Completed
SALE003,CLI003,SEL002,66.00,2025-11-17,Pending
SALE004,CLI004,SEL002,45.00,2025-11-18,Completed
```

### invoices.csv (CORRECTO)
```
INV001,SALE001,2025-11-15,179.98,Paid
INV002,SALE002,2025-11-16,77.50,Pending
INV003,SALE003,2025-11-17,66.00,Pending
INV004,SALE004,2025-11-18,45.00,Paid
```

### warranties.csv (CORRECTO)
```
WAR001,ACC001,24,Full coverage tire warranty,2025-11-15,2027-11-15
WAR002,ACC002,12,Oil filter replacement warranty,2025-11-16,2026-11-16
WAR003,ACC003,12,Air filter replacement warranty,2025-11-17,2026-11-17
WAR004,ACC004,24,Brake pad replacement warranty,2025-11-18,2027-11-18
WAR005,ACC005,36,Battery replacement warranty,2025-11-19,2028-11-19
```

### shopping_carts.csv (CORRECTO)
```
CART001,CLI001,2,0.00,2025-11-20
CART002,CLI002,1,0.00,2025-11-20
CART003,CLI003,3,0.00,2025-11-20
```

---

## 🚀 FLUJO CORRECTO

1. **Inicia la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

2. **Usa Postman para hacer cambios:**
   - GET endpoints para consultar
   - POST endpoints para crear
   - PUT endpoints para actualizar
   - DELETE endpoints para eliminar

3. **NO edites los CSV manualmente**

4. **Si hay errores, reinicia la aplicación:**
   ```bash
   mvn clean spring-boot:run
   ```

---

## ✅ Estado Actual

- ✅ inventory.csv - LIMPIO
- ✅ orders.csv - LIMPIO
- ✅ sales.csv - LIMPIO
- ✅ invoices.csv - LIMPIO
- ✅ warranties.csv - LIMPIO
- ✅ shopping_carts.csv - LIMPIO

**¡Listo para usar!**
