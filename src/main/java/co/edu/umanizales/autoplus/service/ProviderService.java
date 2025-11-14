package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.abstracts.Provider;
import co.edu.umanizales.autoplus.model.entities.InternationalProvider;
import co.edu.umanizales.autoplus.model.entities.LocalProvider;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    private final CsvService csvService;

    public ProviderService(CsvService csvService) {
        this.csvService = csvService;
    }

    public Provider findById(String id) {
        String local = csvService.readLine("local_providers.csv", id);
        if (local != null) {
            LocalProvider p = new LocalProvider();
            p.fromCsv(local);
            return p;
        }
        String intl = csvService.readLine("international_providers.csv", id);
        if (intl != null) {
            InternationalProvider p = new InternationalProvider();
            p.fromCsv(intl);
            return p;
        }
        return null;
    }
}
