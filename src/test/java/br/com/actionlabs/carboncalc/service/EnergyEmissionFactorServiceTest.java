package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.model.EnergyEmissionFactor;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EnergyEmissionFactorServiceTest {
    @Mock
    private EnergyEmissionFactorRepository energyEmissionFactorRepository;
    @InjectMocks
    private EnergyEmissionFactorService energyEmissionFactorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCalculateCarbonEmission() {
        UF uf = UF.MG;
        double energyEmissionFactor = 0.5;  // Mock factor
        int energyConsumption = 10;

        EnergyEmissionFactor mockEnergyEmissionFactor = new EnergyEmissionFactor();
        mockEnergyEmissionFactor.setFactor(energyEmissionFactor);


        when(energyEmissionFactorRepository.findById(uf.toString())).thenReturn(Optional.of(mockEnergyEmissionFactor));

        double result = energyEmissionFactorService.calculateCarbonEmission(energyConsumption, uf);

        // The formula used to calculate the carbon emission is:
        //  Carbon Emission = Energy Consumption * Energy Emission Factor
        assertEquals(5.0, result);
    }

}
