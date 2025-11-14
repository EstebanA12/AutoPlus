package co.edu.umanizales.autoplus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportDTO {
    private ReportePeticiones reporte_peticiones;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportePeticiones {
        private Periodo periodo;
        private Resumen resumen;
        private List<Orden> ordenes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Periodo {
        private String fecha_inicio;
        private String fecha_fin;
        private int dias_cubiertos;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resumen {
        private int total_pedidos;
        private double valor_total;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Orden {
        private String id;
        private String proveedor;
        private double valor_total;
        private String fecha;
        private String estado;
    }
}
