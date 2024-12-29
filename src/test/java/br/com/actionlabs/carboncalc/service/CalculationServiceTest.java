package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.model.Calculation;
import br.com.actionlabs.carboncalc.repository.CalculationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CalculationServiceTest {
    @Mock
    private CalculationRepository calculationRepository;
    @InjectMocks
    private CalculationService calculationService;
    @Mock
    private EnergyEmissionFactorService energyEmissionFactorService;
    @Mock
    private TransportationEmissionFactorService transportationEmissionFactorService;
    @Mock
    private SolidWasteEmissionFactorService solidWasteEmissionFactorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testStartCalc_ValidData() {
        StartCalcRequestDTO data = new StartCalcRequestDTO();
        data.setName("John Cena");
        data.setEmail("john@test.com");
        data.setPhoneNumber("1234567890");
        data.setUf("MG");

        Calculation mockCalculation = new Calculation();
        mockCalculation.setId("abcde");

        when(calculationRepository.save(any(Calculation.class))).thenReturn(mockCalculation);


        StartCalcResponseDTO response = calculationService.startCalc(data);


        assertNotNull(response);
        assertEquals("abcde", response.getId());
        verify(calculationRepository, times(1)).save(any(Calculation.class));
    }


    @Test
    void testStartCalc_InvalidRequest_InvalidFields() {
        StartCalcRequestDTO data = new StartCalcRequestDTO();
        data.setName(null);
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setName("");
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setName("John Cena");

        data.setEmail(null);
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setEmail("");
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setEmail("john@test.com");

        data.setUf(null);
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setUf("");
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setUf("AA");
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setUf("MG");

        data.setPhoneNumber(null);
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setPhoneNumber("");
        assertThrows(IllegalArgumentException.class, () -> calculationService.startCalc(data));
        data.setPhoneNumber("1234567890");

        Calculation mockCalculation = new Calculation();
        mockCalculation.setId("abcde");

        when(calculationRepository.save(any(Calculation.class))).thenReturn(mockCalculation);
        StartCalcResponseDTO response = calculationService.startCalc(data);
        assertNotNull(response);
        assertEquals("abcde", response.getId());
    }

    @Test
    void testUpdateCalcInfo_ValidData() {
        UpdateCalcInfoRequestDTO data = new UpdateCalcInfoRequestDTO();
        data.setId("abcde");
        data.setEnergyConsumption(10);
        data.setSolidWasteTotal(10);
        data.setRecyclePercentage(0.4);

        List<TransportationDTO> transportation = new ArrayList<>();
        TransportationDTO tDTO = new TransportationDTO();
        tDTO.setType("CAR");
        tDTO.setMonthlyDistance(10);
        transportation.add(tDTO);

        data.setTransportation(transportation);

        Calculation mockCalculation = new Calculation();
        mockCalculation.setId("abcde");

        when(calculationRepository.findById("abcde")).thenReturn(Optional.of(mockCalculation));
        when(calculationRepository.save(any(Calculation.class))).thenReturn(mockCalculation);

        boolean result = calculationService.updateCalcInfo(data);

        assertTrue(result);
        verify(calculationRepository, times(1)).findById("abcde");
        verify(calculationRepository, times(1)).save(any(Calculation.class));
    }

    @Test
    void testUpdateCalcInfo_CalculationNotFound() {
        UpdateCalcInfoRequestDTO request = new UpdateCalcInfoRequestDTO();
        request.setId("99999");

        when(calculationRepository.findById("99999")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> calculationService.updateCalcInfo(request));
    }

    @Test
    void testUpdateCalcInfo_InvalidData() {
        UpdateCalcInfoRequestDTO data = new UpdateCalcInfoRequestDTO();
        data.setId("abcde");
        data.setEnergyConsumption(-10);
        assertThrows(IllegalArgumentException.class, () -> calculationService.updateCalcInfo(data));
        data.setEnergyConsumption(10);

        data.setSolidWasteTotal(-10);
        assertThrows(IllegalArgumentException.class, () -> calculationService.updateCalcInfo(data));
        data.setSolidWasteTotal(10);

        data.setRecyclePercentage(1.4);
        assertThrows(IllegalArgumentException.class, () -> calculationService.updateCalcInfo(data));
        data.setRecyclePercentage(-0.4);
        assertThrows(IllegalArgumentException.class, () -> calculationService.updateCalcInfo(data));
        data.setRecyclePercentage(0.4);

        List<TransportationDTO> transportation = new ArrayList<>();
        TransportationDTO tDTO = new TransportationDTO();
        tDTO.setType("TRUCK");
        tDTO.setMonthlyDistance(-10);
        transportation.add(tDTO);

        data.setTransportation(transportation);
        assertThrows(IllegalArgumentException.class, () -> calculationService.updateCalcInfo(data));
        data.getTransportation().get(0).setType("CAR");
        assertThrows(IllegalArgumentException.class, () -> calculationService.updateCalcInfo(data));
        data.getTransportation().get(0).setMonthlyDistance(10);

        Calculation mockCalculation = new Calculation();
        mockCalculation.setId("abcde");
        when(calculationRepository.findById("abcde")).thenReturn(Optional.of(mockCalculation));
        when(calculationRepository.save(any(Calculation.class))).thenReturn(mockCalculation);

        boolean result = calculationService.updateCalcInfo(data);
        assertTrue(result);
    }

    @Test
    void testGetCarbonFootprint_ValidId() {
        Calculation mockCalculation = new Calculation();
        mockCalculation.setId("abcde");
        mockCalculation.setEnergyConsumption(50);
        mockCalculation.setUf(UF.MG);
        mockCalculation.setSolidWasteTotal(100);
        mockCalculation.setRecyclePercentage(0.3);

        List<TransportationDTO> transportation = new ArrayList<>();
        TransportationDTO tDTO = new TransportationDTO();
        tDTO.setType("CAR");
        tDTO.setMonthlyDistance(200);
        transportation.add(tDTO);

        mockCalculation.setTransportation(transportation);

        // Those are arbitrary mock values
        when(calculationRepository.findById("abcde")).thenReturn(Optional.of(mockCalculation));
        when(energyEmissionFactorService.calculateCarbonEmission(50, UF.MG)).thenReturn(25.0);
        when(transportationEmissionFactorService.calculateCarbonEmission(200, TransportationType.CAR)).thenReturn(100.0);
        when(solidWasteEmissionFactorService.calculateCarbonEmission(100, 0.3, UF.MG)).thenReturn(50.0);

        // Act
        CarbonCalculationResultDTO result = calculationService.getCarbonFootprint("abcde");

        // Assert
        assertNotNull(result);
        assertEquals(25.0, result.getEnergy());
        assertEquals(100.0, result.getTransportation());
        assertEquals(50.0, result.getSolidWaste());
        assertEquals(175.0, result.getTotal());
    }

    @Test
    void testGetCarbonFootprint_InvalidId() {
        when(calculationRepository.findById("99999")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> calculationService.getCarbonFootprint("99999"));
    }


}
