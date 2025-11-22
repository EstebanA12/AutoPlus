# 📊 Tabla Visual en POST - Guía Completa

## ✅ Endpoint para Ver la Tabla

```
POST http://localhost:8080/api/orders/report/generate-html
```

---

## 📝 Pasos Exactos en Postman

### Paso 1: Abre Postman

### Paso 2: Crea una nueva solicitud
- Click en "+" o "New"
- Selecciona "Request"

### Paso 3: Configura como POST
- En el dropdown de método, selecciona **POST**

### Paso 4: Ingresa la URL
```
http://localhost:8080/api/orders/report/generate-html
```

### Paso 5: Ve a la pestaña "Headers"
- Agrega esta línea:
  - **Key:** `Content-Type`
  - **Value:** `application/json`

### Paso 6: Ve a la pestaña "Body"
- Selecciona **raw**
- En el dropdown de la derecha, selecciona **JSON**

### Paso 7: Copia y pega este JSON

```json
{
  "fecha_inicio": "2025-11-15",
  "fecha_fin": "2025-11-19"
}
```

### Paso 8: Click en "Send"

---

## 🎯 Lo que Verás

Después de hacer click en "Send", verás:

1. **Pestaña "Body"** - Mostrará el HTML en texto
2. **Pestaña "Preview"** - Mostrará la tabla formateada visualmente ⭐

### En la pestaña "Preview" verás:

- **Título:** 📊 Reporte de Pedidos
- **Período del Reporte:**
  - Fecha Inicio: 2025-11-15
  - Fecha Fin: 2025-11-19
  - Días Cubiertos: 5 días

- **Resumen:**
  - Total de Pedidos: 5
  - Valor Total: $12,899.00

- **Tabla con los Pedidos:**
  | ID Pedido | Proveedor | Fecha | Estado | Valor Total |
  |-----------|-----------|-------|--------|-------------|
  | ORD001 | PROV001 | 2025-11-15 | Pending | $4,499.50 |
  | ORD002 | PROV002 | 2025-11-16 | Delivered | $1,550.00 |
  | ORD003 | PROV003 | 2025-11-17 | In Transit | $1,650.00 |
  | ORD004 | PROV004 | 2025-11-18 | Pending | $1,350.00 |
  | ORD005 | PROV005 | 2025-11-19 | Completed | $2,850.00 |

---

## 🎨 Características de la Tabla

✅ **Colores por Estado:**
- 🟨 Pending (Amarillo)
- 🟩 Delivered (Verde)
- 🟦 In Transit (Azul)
- 🟩 Completed (Verde oscuro)

✅ **Diseño Profesional:**
- Tabla con bordes
- Hover effect en las filas
- Valores alineados a la derecha
- Formato de moneda ($)

✅ **Información Completa:**
- Período del reporte
- Resumen de totales
- Detalle de cada pedido

---

## 📌 Otros Ejemplos para Probar

### Ejemplo 2: Reporte de 3 días

```json
{
  "fecha_inicio": "2025-11-16",
  "fecha_fin": "2025-11-18"
}
```

**Resultado esperado:**
- Total de Pedidos: 3
- Valor Total: $4,550.00

### Ejemplo 3: Un solo día

```json
{
  "fecha_inicio": "2025-11-17",
  "fecha_fin": "2025-11-17"
}
```

**Resultado esperado:**
- Total de Pedidos: 1
- Valor Total: $1,650.00

---

## ⚠️ Importante

✅ **El servidor debe estar corriendo:**
```bash
mvn spring-boot:run
```

✅ **Formato de fechas:** `YYYY-MM-DD`

✅ **Haz click en "Preview"** para ver la tabla visual

✅ **Si no ves nada:**
1. Verifica que el Status sea "200 OK"
2. Asegúrate de que las fechas sean válidas
3. Reinicia el servidor si es necesario

---

## 🔗 Otros Endpoints Disponibles

### 1. Reporte en JSON (datos puros)
```
POST http://localhost:8080/api/orders/report/generate
```

### 2. Reporte en Tabla JSON (fácil de leer)
```
POST http://localhost:8080/api/orders/report/tabla
```

### 3. Reporte en HTML Visual (RECOMENDADO) ⭐
```
POST http://localhost:8080/api/orders/report/generate-html
```
