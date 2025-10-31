package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.abstracts.Employee;

/**
 * Class representing a seller (extends Employee)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller extends Employee {
    private double salesCommission;
    private int salesCount;

    @Override
    public double calculateBonus() {
        return getSalary() * 0.1 + (salesCommission * salesCount);
    }

    @Override
    public String toCsv() {
        return super.toCsv() + "," + salesCommission + "," + salesCount;
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 7) {
            this.setId(parts[0]);
            this.setName(parts[1]);
            this.setEmail(parts[2]);
            this.setSalary(Double.parseDouble(parts[3]));
            this.setDepartment(parts[4]);
            this.salesCommission = Double.parseDouble(parts[5]);
            this.salesCount = Integer.parseInt(parts[6]);
        }
    }
}
