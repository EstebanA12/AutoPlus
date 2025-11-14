# ⚠️ ADVERTENCIA CRÍTICA - NO EDITAR CSV

## 🚨 IMPORTANTE

**NO EDITES LOS ARCHIVOS CSV DIRECTAMENTE EN EL IDE**

Cada vez que editas un CSV manualmente, se corrompen los datos:
- ❌ Se introducen UUIDs inválidos
- ❌ Se introducen valores `null`
- ❌ Se rompen las relaciones
- ❌ Los GET endpoints retornan datos null

---

## 📋 Archivos CSV - PROHIBIDO EDITAR

```
data/csv/
├── technological_accessories.csv     ⛔ NO TOCAR
├── interior_accessories.csv          ⛔ NO TOCAR
├── exterior_accessories.csv          ⛔ NO TOCAR
├── local_providers.csv               ⛔ NO TOCAR
├── international_providers.csv       ⛔ NO TOCAR
├── clients.csv                       ⛔ NO TOCAR
├── sellers.csv                       ⛔ NO TOCAR
├── inventory.csv                     ⛔ NO TOCAR
├── orders.csv                        ⛔ NO TOCAR
├── shopping_carts.csv                ⛔ NO TOCAR
├── sales.csv                         ⛔ NO TOCAR
├── invoices.csv                      ⛔ NO TOCAR
├── warranties.csv                    ⛔ NO TOCAR
├── cart_items.csv                    ⛔ NO TOCAR
└── sale_items.csv                    ⛔ NO TOCAR
```

---

## ✅ DATOS CORRECTOS - REFERENCIA FINAL

### inventory.csv (CORRECTO - NO MODIFICAR)
```
INV001,ACC001,150,Warehouse A,2025-11-20
INV002,ACC002,300,Warehouse B,2025-11-20
INV003,ACC003,200,Warehouse A,2025-11-20
INV004,ACC004,100,Warehouse C,2025-11-20
INV005,ACC005,50,Warehouse B,2025-11-20
INV006,ACC006,250,Warehouse A,2025-11-20
```

### orders.csv (CORRECTO - NO MODIFICAR)
```
ORD001,PROV001,ACC001,50,4499.50,2025-11-15,Pending
ORD002,PROV002,ACC002,100,1550.00,2025-11-16,Delivered
ORD003,PROV003,ACC003,75,1650.00,2025-11-17,In Transit
ORD004,PROV004,ACC004,30,1350.00,2025-11-18,Pending
```

### sales.csv (CORRECTO - NO MODIFICAR)
```
SALE001,CLI001,SEL001,179.98,2025-11-15,Completed
SALE002,CLI002,SEL001,77.50,2025-11-16,Completed
SALE003,CLI003,SEL002,66.00,2025-11-17,Pending
SALE004,CLI004,SEL002,45.00,2025-11-18,Completed
```

### invoices.csv (CORRECTO - NO MODIFICAR)
```
INV001,SALE001,2025-11-15,179.98,Paid
INV002,SALE002,2025-11-16,77.50,Pending
INV003,SALE003,2025-11-17,66.00,Pending
INV004,SALE004,2025-11-18,45.00,Paid
```

### warranties.csv (CORRECTO - NO MODIFICAR)
```
WAR001,ACC001,24,Full coverage tire warranty,2025-11-15,2027-11-15
WAR002,ACC002,12,Oil filter replacement warranty,2025-11-16,2026-11-16
WAR003,ACC003,12,Air filter replacement warranty,2025-11-17,2026-11-17
WAR004,ACC004,24,Brake pad replacement warranty,2025-11-18,2027-11-18
WAR005,ACC005,36,Battery replacement warranty,2025-11-19,2028-11-19
```

### shopping_carts.csv (CORRECTO - NO MODIFICAR)
```
CART001,CLI001,2,0.00,2025-11-20
CART002,CLI002,1,0.00,2025-11-20
CART003,CLI003,3,0.00,2025-11-20
```

---

## 🔴 SI EDITAS UN CSV

Si accidentalmente editas un CSV:

1. **Cierra el archivo sin guardar**
   - Presiona Ctrl+Z para deshacer
   - O cierra sin guardar

2. **Si ya guardaste:**
   - Restaura el contenido correcto
   - Copia el contenido de arriba
   - Reemplaza el contenido del CSV

3. **Reinicia la aplicación:**
   ```bash
   mvn clean spring-boot:run
   ```

---

## ✅ FLUJO CORRECTO

### Para CONSULTAR datos:
```bash
curl -X GET http://localhost:8080/api/inventory
```

### Para CREAR datos:
```bash
curl -X POST http://localhost:8080/api/inventory \
  -H "Content-Type: application/json" \
  -d '{"accessoryId":"ACC001","quantity":100,"location":"Warehouse A","lastUpdate":"2025-11-20"}'
```

### Para ACTUALIZAR datos:
```bash
curl -X PUT http://localhost:8080/api/inventory/INV001 \
  -H "Content-Type: application/json" \
  -d '{"accessoryId":"ACC001","quantity":200,"location":"Warehouse B","lastUpdate":"2025-11-20"}'
```

**NUNCA edites los CSV manualmente**

---

## 🎯 RESUMEN

- ✅ Los CSV están correctos
- ✅ Los servicios están corregidos
- ✅ Los GET endpoints funcionan
- ✅ NO EDITES LOS CSV

**¡Listo para usar!**
