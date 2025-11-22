# 📊 Cómo Usar el Reporte POST - Guía Rápida

## ✅ Endpoint Recomendado (Usa Este)

```
POST http://localhost:8080/api/orders/report/tabla
```

---

## 📝 Pasos en Postman

### 1. Abre Postman

### 2. Crea una nueva solicitud POST
- Click en "+" o "New"
- Selecciona "Request"

### 3. Configura la URL
```
http://localhost:8080/api/orders/report/tabla
```

### 4. Selecciona el método
- Cambia a **POST**

### 5. Ve a la pestaña "Headers"
- Agrega: `Content-Type: application/json`

### 6. Ve a la pestaña "Body"
- Selecciona **raw**
- Selecciona **JSON** en el dropdown

### 7. Copia y pega este JSON en el Body

```json
{
  "fecha_inicio": "2025-11-15",
  "fecha_fin": "2025-11-19"
}
```

### 8. Click en "Send"

---

## 📊 Respuesta que Verás

En el Body de la respuesta verás:

```json
{
  "reporte_peticiones": {
    "periodo": {
      "fecha_inicio": "2025-11-15",
      "fecha_fin": "2025-11-19",
      "dias_cubiertos": 5
    },
    "resumen": {
      "total_pedidos": 5,
      "valor_total": 12899.00
    },
    "ordenes": [
      {
        "id": "ORD001",
        "proveedor": "PROV001",
        "valor_total": 4499.50,
        "fecha": "2025-11-15",
        "estado": "Pending"
      },
      {
        "id": "ORD002",
        "proveedor": "PROV002",
        "valor_total": 1550.00,
        "fecha": "2025-11-16",
        "estado": "Delivered"
      },
      {
        "id": "ORD003",
        "proveedor": "PROV003",
        "valor_total": 1650.00,
        "fecha": "2025-11-17",
        "estado": "In Transit"
      },
      {
        "id": "ORD004",
        "proveedor": "PROV004",
        "valor_total": 1350.00,
        "fecha": "2025-11-18",
        "estado": "Pending"
      },
      {
        "id": "ORD005",
        "proveedor": "PROV005",
        "valor_total": 2850.00,
        "fecha": "2025-11-19",
        "estado": "Completed"
      }
    ]
  }
}
```

---

## 🎯 Otros Endpoints Disponibles

### Opción 2: Reporte en JSON (igual al anterior)
```
POST http://localhost:8080/api/orders/report/generate
```

### Opción 3: Reporte en HTML (para ver en navegador)
```
POST http://localhost:8080/api/orders/report/generate-html
```
- Respuesta: Tabla HTML formateada
- Ver en: Postman → Preview o copiar URL en navegador

---

## ⚠️ Importante

✅ El servidor debe estar corriendo:
```bash
mvn spring-boot:run
```

✅ Las fechas deben estar en formato: `YYYY-MM-DD`

✅ Si no hay pedidos en el rango, retorna un reporte vacío

✅ El reporte filtra automáticamente los pedidos dentro del rango de fechas

---

## 🔍 Solución de Problemas

### Si no ves nada en el Body:
1. Verifica que el servidor esté corriendo
2. Comprueba que la URL sea exacta
3. Asegúrate de que el JSON del Body sea válido
4. Revisa la pestaña "Status" - debe decir "200 OK"

### Si ves un error 400:
- Verifica que `fecha_inicio` y `fecha_fin` estén presentes
- Verifica que las fechas estén en formato `YYYY-MM-DD`

### Si ves un error 500:
- Revisa la consola del servidor para ver el error
- Asegúrate de que los datos en `orders.csv` sean válidos
