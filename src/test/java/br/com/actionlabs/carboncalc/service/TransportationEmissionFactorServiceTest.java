package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.model.EnergyEmissionFactor;
import br.com.actionlabs.carboncalc.model.TransportationEmissionFactor;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
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
public class TransportationEmissionFactorServiceTest {
    @Mock
    private TransportationEmissionFactorRepository transportationEmissionFactorRepository;
    @InjectMocks
    private TransportationEmissionFactorService transportationEmissionFactorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCalculateCarbonEmission() {
        TransportationType testType = TransportationType.CAR;
        double distance = 10;
        double emissionFactor = 0.5;  // Mock factor

        TransportationEmissionFactor mockEmissionFactor = new TransportationEmissionFactor();
        mockEmissionFactor.setFactor(emissionFactor);


        when(transportationEmissionFactorRepository.findById(testType)).thenReturn(Optional.of(mockEmissionFactor));

        double result = transportationEmissionFactorService.calculateCarbonEmission(distance, testType);

        // The formula used to calculate the carbon emission is:
        //  Carbon Emission = Distance * Transportation Emission Factor
        assertEquals(5.0, result);
    }

}
