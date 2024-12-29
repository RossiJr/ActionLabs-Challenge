package br.com.actionlabs.carboncalc.model;

import br.com.actionlabs.carboncalc.enums.UF;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document("calculation")
public class Calculation {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private UF uf;
    private Instant createdAt;
    private Instant updatedAt;
}
