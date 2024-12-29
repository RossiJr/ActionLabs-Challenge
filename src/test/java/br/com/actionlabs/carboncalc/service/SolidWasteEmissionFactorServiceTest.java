package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
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
public class SolidWasteEmissionFactorServiceTest {
    @Mock
    private SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;
    @InjectMocks
    private SolidWasteEmissionFactorService solidWasteEmissionFactorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCalculateCarbonEmission() {
        UF uf = UF.MG;
        int solidWaste = 10;
        double recyclePercentage = 0.4;

        // Mock factors
        double recyclableFactor = 0.3;
        double nonRecyclableFactor = 0.97;

        SolidWasteEmissionFactor mockEmissionFactor = new SolidWasteEmissionFactor();
        mockEmissionFactor.setRecyclableFactor(recyclableFactor);
        mockEmissionFactor.setNonRecyclableFactor(nonRecyclableFactor);

        when(solidWasteEmissionFactorRepository.findById(uf.toString())).thenReturn(Optional.of(mockEmissionFactor));

        double result = solidWasteEmissionFactorService.calculateCarbonEmission(solidWaste, recyclePercentage, uf);

        // The formula used to calculate the carbon emission is:
        //  Carbon Emission = (Solid Waste * Recycle Percentage * Recyclable Factor)
        //      + (Solid Waste * (1 - Recycle Percentage) * Non-Recyclable Factor)
        assertEquals(7.02, result);
    }

}
