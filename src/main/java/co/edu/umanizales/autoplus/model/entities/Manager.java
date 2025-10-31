package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.abstracts.Employee;

/**
 * Class representing a manager (extends Employee)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends Employee {
    private int teamSize;
    private double performanceBonus;

    @Override
    public double calculateBonus() {
        return getSalary() * 0.15 + performanceBonus;
    }

    @Override
    public String toCsv() {
        return super.toCsv() + "," + teamSize + "," + performanceBonus;
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
            this.teamSize = Integer.parseInt(parts[5]);
            this.performanceBonus = Double.parseDouble(parts[6]);
        }
    }
}
