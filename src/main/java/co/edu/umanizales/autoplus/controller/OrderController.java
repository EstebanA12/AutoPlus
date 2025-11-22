package co.edu.umanizales.autoplus.controller;

import co.edu.umanizales.autoplus.model.entities.Order;
import co.edu.umanizales.autoplus.model.dto.OrderReportDTO;
import co.edu.umanizales.autoplus.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;
import co.edu.umanizales.autoplus.service.ProviderService;
import co.edu.umanizales.autoplus.service.AccessoryService;
import co.edu.umanizales.autoplus.model.abstracts.Provider;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProviderService providerService;
    private final AccessoryService accessoryService;

    public OrderController(OrderService orderService, ProviderService providerService, AccessoryService accessoryService) {
        this.orderService = orderService;
        this.providerService = providerService;
        this.accessoryService = accessoryService;
    }

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable String id) {
        Order order = orderService.findById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Order> create(@RequestBody JsonNode body) {
        String providerId = null;
        if (body.has("provider") && body.get("provider").has("id")) {
            providerId = body.get("provider").get("id").asText();
        } else if (body.has("providerId")) {
            providerId = body.get("providerId").asText();
        }
        String accessoryId = null;
        if (body.has("accessory") && body.get("accessory").has("id")) {
            accessoryId = body.get("accessory").get("id").asText();
        } else if (body.has("accessoryId")) {
            accessoryId = body.get("accessoryId").asText();
        }
        if (providerId == null || providerId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "providerId es requerido");
        }
        if (accessoryId == null || accessoryId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accessoryId es requerido");
        }

        Provider provider = providerService.findById(providerId);
        if (provider == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor no encontrado: " + providerId);
        }
        Accessory accessory = accessoryService.findById(accessoryId);
        if (accessory == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accesorio no encontrado: " + accessoryId);
        }

        int quantity = body.has("quantity") ? body.get("quantity").asInt() : 1;
        if (quantity < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity debe ser >= 1");
        }
        double totalCost = body.has("totalCost") ? body.get("totalCost").asDouble() : accessory.getPrice() * quantity;

        Order order = new Order();
        order.setProvider(provider);
        order.setAccessory(accessory);
        order.setQuantity(quantity);
        order.setTotalCost(totalCost);
        if (body.has("orderDate")) order.setOrderDate(body.get("orderDate").asText());
        if (body.has("status")) order.setStatus(body.get("status").asText());

        Order created = orderService.create(order);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getId())).body(created);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Order> update(@PathVariable String id, @RequestBody JsonNode body) {
        Order existing = orderService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (body.has("provider") && body.get("provider").has("id")) {
            String providerId = body.get("provider").get("id").asText();
            Provider provider = providerService.findById(providerId);
            if (provider == null) return ResponseEntity.badRequest().build();
            existing.setProvider(provider);
        } else if (body.has("providerId")) {
            String providerId = body.get("providerId").asText();
            Provider provider = providerService.findById(providerId);
            if (provider == null) return ResponseEntity.badRequest().build();
            existing.setProvider(provider);
        }
        if (body.has("accessory") && body.get("accessory").has("id")) {
            String accessoryId = body.get("accessory").get("id").asText();
            Accessory accessory = accessoryService.findById(accessoryId);
            if (accessory == null) return ResponseEntity.badRequest().build();
            existing.setAccessory(accessory);
        } else if (body.has("accessoryId")) {
            String accessoryId = body.get("accessoryId").asText();
            Accessory accessory = accessoryService.findById(accessoryId);
            if (accessory == null) return ResponseEntity.badRequest().build();
            existing.setAccessory(accessory);
        }
        if (body.has("quantity")) existing.setQuantity(body.get("quantity").asInt());
        if (body.has("totalCost")) existing.setTotalCost(body.get("totalCost").asDouble());
        if (body.has("orderDate")) existing.setOrderDate(body.get("orderDate").asText());
        if (body.has("status")) existing.setStatus(body.get("status").asText());

        Order updated = orderService.update(existing);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/report/orders")
    public ResponseEntity<?> getOrderReport() {
        List<Order> orders = orderService.findAll();
        List<Map<String, Object>> report = new ArrayList<>();
        
        for (Order order : orders) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", order.getId());
            row.put("fecha", order.getOrderDate());
            row.put("estado", order.getStatus());
            row.put("valor", order.getTotalCost());
            report.add(row);
        }
        
        return ResponseEntity.ok(report);
    }

    @GetMapping(value = "/report/tabla-html", produces = "text/html; charset=UTF-8")
    public String getOrderReportHtml() {
        try {
            List<Order> orders = orderService.findAll();
            if (orders == null || orders.isEmpty()) {
                return "<h1>No hay pedidos disponibles</h1>";
            }
            return generateSimpleHtmlTable(orders);
        } catch (Exception e) {
            return "<h1>Error: " + e.getMessage() + "</h1>";
        }
    }

    @GetMapping(value = "/report/test-html", produces = "text/html; charset=UTF-8")
    public String testHtml() {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Test</title></head><body><h1>✅ Servidor Funcionando</h1><p>Si ves esto, el servidor está respondiendo correctamente.</p></body></html>";
    }

    @PostMapping("/report/generate")
    public ResponseEntity<?> generateReport(@RequestBody Map<String, String> request) {
        try {
            String fechaInicio = request.get("fecha_inicio");
            String fechaFin = request.get("fecha_fin");
            
            if (fechaInicio == null || fechaInicio.isBlank()) {
                return ResponseEntity.badRequest().body("fecha_inicio es requerida");
            }
            if (fechaFin == null || fechaFin.isBlank()) {
                return ResponseEntity.badRequest().body("fecha_fin es requerida");
            }
            
            OrderReportDTO report = orderService.generateReport(fechaInicio, fechaFin);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error generando reporte: " + e.getMessage());
        }
    }

    @PostMapping(value = "/report/generate-html", produces = "text/html; charset=UTF-8")
    public ResponseEntity<String> generateReportHtml(@RequestBody Map<String, String> request) {
        try {
            String fechaInicio = request.get("fecha_inicio");
            String fechaFin = request.get("fecha_fin");
            
            if (fechaInicio == null || fechaInicio.isBlank()) {
                return ResponseEntity.badRequest().body("<h1>Error: fecha_inicio es requerida</h1>");
            }
            if (fechaFin == null || fechaFin.isBlank()) {
                return ResponseEntity.badRequest().body("<h1>Error: fecha_fin es requerida</h1>");
            }
            
            OrderReportDTO report = orderService.generateReport(fechaInicio, fechaFin);
            String html = generateHtmlReport(report);
            return ResponseEntity.ok()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(html);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<h1>Error generando reporte: " + e.getMessage() + "</h1>");
        }
    }

    @PostMapping("/report/tabla")
    public ResponseEntity<?> generateReportTable(@RequestBody Map<String, String> request) {
        try {
            String fechaInicio = request.get("fecha_inicio");
            String fechaFin = request.get("fecha_fin");
            
            if (fechaInicio == null || fechaInicio.isBlank()) {
                return ResponseEntity.badRequest().body("fecha_inicio es requerida");
            }
            if (fechaFin == null || fechaFin.isBlank()) {
                return ResponseEntity.badRequest().body("fecha_fin es requerida");
            }
            
            OrderReportDTO report = orderService.generateReport(fechaInicio, fechaFin);
            
            Map<String, Object> response = new HashMap<>();
            response.put("reporte_peticiones", report.getReporte_peticiones());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error generando reporte: " + e.getMessage());
        }
    }

    private String generateHtmlReport(OrderReportDTO report) {
        StringBuilder html = new StringBuilder();
        OrderReportDTO.ReportePeticiones rp = report.getReporte_peticiones();
        
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"es\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>Reporte de Pedidos</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
        html.append("        .container { max-width: 1000px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #333; text-align: center; }\n");
        html.append("        .periodo { background-color: #e3f2fd; padding: 15px; border-radius: 5px; margin-bottom: 20px; }\n");
        html.append("        .resumen { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 20px; }\n");
        html.append("        .resumen-item { background-color: #f9f9f9; padding: 15px; border-left: 4px solid #2196F3; }\n");
        html.append("        .resumen-item h3 { margin: 0 0 10px 0; color: #2196F3; }\n");
        html.append("        .resumen-item p { margin: 0; font-size: 24px; font-weight: bold; color: #333; }\n");
        html.append("        table { width: 100%; border-collapse: collapse; margin-top: 20px; }\n");
        html.append("        th { background-color: #2196F3; color: white; padding: 12px; text-align: left; }\n");
        html.append("        td { padding: 12px; border-bottom: 1px solid #ddd; }\n");
        html.append("        tr:hover { background-color: #f5f5f5; }\n");
        html.append("        .estado-pending { background-color: #fff3cd; padding: 5px 10px; border-radius: 3px; }\n");
        html.append("        .estado-delivered { background-color: #d4edda; padding: 5px 10px; border-radius: 3px; }\n");
        html.append("        .estado-transit { background-color: #cfe2ff; padding: 5px 10px; border-radius: 3px; }\n");
        html.append("        .estado-completed { background-color: #d1e7dd; padding: 5px 10px; border-radius: 3px; }\n");
        html.append("        .valor { text-align: right; font-weight: bold; color: #27ae60; }\n");
        html.append("        .periodo-info { display: grid; grid-template-columns: repeat(3, 1fr); gap: 10px; }\n");
        html.append("        .periodo-info p { margin: 5px 0; }\n");
        html.append("        .periodo-info strong { color: #2196F3; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>📊 Reporte de Pedidos</h1>\n");
        
        // Período
        html.append("        <div class=\"periodo\">\n");
        html.append("            <h2>Período del Reporte</h2>\n");
        html.append("            <div class=\"periodo-info\">\n");
        html.append("                <p><strong>Fecha Inicio:</strong> ").append(rp.getPeriodo().getFecha_inicio()).append("</p>\n");
        html.append("                <p><strong>Fecha Fin:</strong> ").append(rp.getPeriodo().getFecha_fin()).append("</p>\n");
        html.append("                <p><strong>Días Cubiertos:</strong> ").append(rp.getPeriodo().getDias_cubiertos()).append(" días</p>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        
        // Resumen
        html.append("        <div class=\"resumen\">\n");
        html.append("            <div class=\"resumen-item\">\n");
        html.append("                <h3>Total de Pedidos</h3>\n");
        html.append("                <p>").append(rp.getResumen().getTotal_pedidos()).append("</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"resumen-item\">\n");
        html.append("                <h3>Valor Total</h3>\n");
        html.append("                <p>$").append(String.format("%.2f", rp.getResumen().getValor_total())).append("</p>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        
        // Tabla
        html.append("        <h2>Detalle de Pedidos</h2>\n");
        html.append("        <table>\n");
        html.append("            <thead>\n");
        html.append("                <tr>\n");
        html.append("                    <th>ID Pedido</th>\n");
        html.append("                    <th>Proveedor</th>\n");
        html.append("                    <th>Fecha</th>\n");
        html.append("                    <th>Estado</th>\n");
        html.append("                    <th class=\"valor\">Valor Total</th>\n");
        html.append("                </tr>\n");
        html.append("            </thead>\n");
        html.append("            <tbody>\n");
        
        for (OrderReportDTO.Orden orden : rp.getOrdenes()) {
            String estadoClass = getEstadoClass(orden.getEstado());
            html.append("                <tr>\n");
            html.append("                    <td><strong>").append(orden.getId()).append("</strong></td>\n");
            html.append("                    <td>").append(orden.getProveedor()).append("</td>\n");
            html.append("                    <td>").append(orden.getFecha()).append("</td>\n");
            html.append("                    <td><span class=\"").append(estadoClass).append("\">").append(orden.getEstado()).append("</span></td>\n");
            html.append("                    <td class=\"valor\">$").append(String.format("%.2f", orden.getValor_total())).append("</td>\n");
            html.append("                </tr>\n");
        }
        
        html.append("            </tbody>\n");
        html.append("        </table>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        return html.toString();
    }

    private String getEstadoClass(String estado) {
        if (estado == null) return "estado-pending";
        switch (estado.toLowerCase()) {
            case "pending":
                return "estado-pending";
            case "delivered":
                return "estado-delivered";
            case "in transit":
                return "estado-transit";
            case "completed":
                return "estado-completed";
            default:
                return "estado-pending";
        }
    }

    private String generateSimpleHtmlTable(List<Order> orders) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"es\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>Reporte de Pedidos</title>\n");
        html.append("    <style>\n");
        html.append("        * { margin: 0; padding: 0; box-sizing: border-box; }\n");
        html.append("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }\n");
        html.append("        .container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 10px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); overflow: hidden; }\n");
        html.append("        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; }\n");
        html.append("        .header h1 { font-size: 32px; margin-bottom: 10px; }\n");
        html.append("        .content { padding: 30px; }\n");
        html.append("        table { width: 100%; border-collapse: collapse; margin-top: 20px; }\n");
        html.append("        th { background-color: #667eea; color: white; padding: 15px; text-align: left; font-weight: 600; }\n");
        html.append("        td { padding: 12px 15px; border-bottom: 1px solid #eee; }\n");
        html.append("        tr:hover { background-color: #f8f9ff; }\n");
        html.append("        .estado { padding: 6px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; display: inline-block; }\n");
        html.append("        .estado-pending { background-color: #fff3cd; color: #856404; }\n");
        html.append("        .estado-delivered { background-color: #d4edda; color: #155724; }\n");
        html.append("        .estado-transit { background-color: #cfe2ff; color: #084298; }\n");
        html.append("        .estado-completed { background-color: #d1e7dd; color: #0f5132; }\n");
        html.append("        .valor { text-align: right; font-weight: 600; color: #667eea; }\n");
        html.append("        .stats { display: grid; grid-template-columns: repeat(2, 1fr); gap: 20px; margin-bottom: 30px; }\n");
        html.append("        .stat-card { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 8px; text-align: center; }\n");
        html.append("        .stat-card h3 { font-size: 14px; margin-bottom: 10px; opacity: 0.9; }\n");
        html.append("        .stat-card .value { font-size: 32px; font-weight: bold; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <div class=\"header\">\n");
        html.append("            <h1>📊 Reporte de Pedidos</h1>\n");
        html.append("            <p>Todos los pedidos registrados en el sistema</p>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"content\">\n");
        
        // Stats
        double totalValue = 0;
        for (Order order : orders) {
            totalValue += order.getTotalCost();
        }
        
        html.append("            <div class=\"stats\">\n");
        html.append("                <div class=\"stat-card\">\n");
        html.append("                    <h3>Total de Pedidos</h3>\n");
        html.append("                    <div class=\"value\">").append(orders.size()).append("</div>\n");
        html.append("                </div>\n");
        html.append("                <div class=\"stat-card\">\n");
        html.append("                    <h3>Valor Total</h3>\n");
        html.append("                    <div class=\"value\">$").append(String.format("%.2f", totalValue)).append("</div>\n");
        html.append("                </div>\n");
        html.append("            </div>\n");
        
        // Table
        html.append("            <table>\n");
        html.append("                <thead>\n");
        html.append("                    <tr>\n");
        html.append("                        <th>ID Pedido</th>\n");
        html.append("                        <th>Proveedor</th>\n");
        html.append("                        <th>Fecha</th>\n");
        html.append("                        <th>Estado</th>\n");
        html.append("                        <th class=\"valor\">Valor Total</th>\n");
        html.append("                    </tr>\n");
        html.append("                </thead>\n");
        html.append("                <tbody>\n");
        
        for (Order order : orders) {
            String estadoClass = getEstadoClass(order.getStatus());
            String proveedorId = order.getProviderId() != null ? order.getProviderId() : "N/A";
            html.append("                    <tr>\n");
            html.append("                        <td><strong>").append(order.getId()).append("</strong></td>\n");
            html.append("                        <td>").append(proveedorId).append("</td>\n");
            html.append("                        <td>").append(order.getOrderDate()).append("</td>\n");
            html.append("                        <td><span class=\"estado ").append(estadoClass).append("\">").append(order.getStatus()).append("</span></td>\n");
            html.append("                        <td class=\"valor\">$").append(String.format("%.2f", order.getTotalCost())).append("</td>\n");
            html.append("                    </tr>\n");
        }
        
        html.append("                </tbody>\n");
        html.append("            </table>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        return html.toString();
    }
}
