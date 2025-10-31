package co.edu.umanizales.autoplus.model.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Abstract class representing an employee
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Employee implements Persistable {
    private String id;
    private String name;
    private String email;
    private double salary;
    private String department;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%.2f,%s",
                id, name, email, salary, department);
    }

    public abstract double calculateBonus();
}
