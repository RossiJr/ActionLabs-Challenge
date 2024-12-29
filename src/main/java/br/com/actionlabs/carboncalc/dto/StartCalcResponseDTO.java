package br.com.actionlabs.carboncalc.dto;

import lombok.Data;

@Data
public class StartCalcResponseDTO {
    private String id;

    public StartCalcResponseDTO(String id) {
        this.id = id;
    }
}
