package co.edu.umanizales.autoplus.model.interfaces;

/**
 * Interface that defines behavior for persistable objects
 */
public interface Persistable {
    String toCsv();
    void fromCsv(String csvLine);
}
