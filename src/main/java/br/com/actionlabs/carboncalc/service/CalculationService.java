package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.model.Calculation;
import br.com.actionlabs.carboncalc.repository.CalculationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CalculationService {
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private EnergyEmissionFactorService energyEmissionFactorService;
    @Autowired
    private TransportationEmissionFactorService transportationEmissionFactorService;
    @Autowired
    private SolidWasteEmissionFactorService solidWasteEmissionFactorService;

    private static final Logger logger = LoggerFactory.getLogger(CalculationService.class);


    /**
     * Starts a new calculation, validating if the required initial fields are present
     *
     * @param calcRequestDTO the request with the initial required data
     * @return the response with the calculation id
     */
    public StartCalcResponseDTO startCalc(StartCalcRequestDTO calcRequestDTO) {
        if (calcRequestDTO == null) {
            throw new IllegalArgumentException("Calculation cannot be empty");
        }

        if (calcRequestDTO.getName() == null || calcRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (calcRequestDTO.getEmail() == null || calcRequestDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (calcRequestDTO.getUf() == null || calcRequestDTO.getUf().isEmpty() ||
                UF.fromString(calcRequestDTO.getUf()) == null) {
            throw new IllegalArgumentException("UF must exist");
        }

        // A stronger validation can be implemented in further versions
        if (calcRequestDTO.getPhoneNumber() == null || calcRequestDTO.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        Calculation obj = new Calculation();
        obj.setName(calcRequestDTO.getName());
        obj.setEmail(calcRequestDTO.getEmail());
        obj.setPhoneNumber(calcRequestDTO.getPhoneNumber());
        obj.setUf(UF.fromString(calcRequestDTO.getUf()));
        obj.setCreatedAt(Instant.now());
        obj.setUpdatedAt(Instant.now());

        Calculation calculation = calculationRepository.save(obj);

        logger.info("Calculation started: {}", calculation.getId());

        return new StartCalcResponseDTO(calculation.getId());
    }


    /**
     * Updates the calculation information with the following data:</br>
     * - Energy consumption</br>
     * - Transportation</br>
     * - Solid waste</br>
     * - Recycle percentage
     * @param calcUpdateRequestDTO the request with the new data
     * @return true if the update was successful, throws an exception otherwise
     */
    public boolean updateCalcInfo(UpdateCalcInfoRequestDTO calcUpdateRequestDTO) {
        if (calcUpdateRequestDTO == null) {
            throw new IllegalArgumentException("Calculation cannot be empty");
        }

        if (calcUpdateRequestDTO.getId() == null || calcUpdateRequestDTO.getId().isEmpty()) {
            throw new IllegalArgumentException("Calculation ID cannot be empty");
        }

        Calculation calculation = calculationRepository.findById(calcUpdateRequestDTO.getId()).orElse(null);
        if (calculation == null) {
            throw new IllegalArgumentException("Calculation not found");
        }

        // Not specified in the requirements.
        // As it is a mock project, I will assume that this information will be always positive,
        // as the intention is the consumption, not the generation of energy (in that case it
        // would make sense to have negative values)
        if (calcUpdateRequestDTO.getEnergyConsumption() < 0) {
            throw new IllegalArgumentException("Energy consumption must be positive");
        }

        for (TransportationDTO tDTO : calcUpdateRequestDTO.getTransportation()) {
            if (TransportationType.fromString(tDTO.getType()) == null) {
                throw new IllegalArgumentException("Invalid transportation type");
            }

            // Again, the distance is not specified in the requirements, but I will
            // assume that it must be positive, as does not make sense to have negative
            // distances
            if (tDTO.getMonthlyDistance() < 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
        }

        // As it was not specified in the requirements,
        // I will assume that the solid waste must be positive
        if (calcUpdateRequestDTO.getSolidWasteTotal() < 0) {
            throw new IllegalArgumentException("Solid waste must be positive");
        }

        if (calcUpdateRequestDTO.getRecyclePercentage() < 0 || calcUpdateRequestDTO.getRecyclePercentage() > 1) {
            throw new IllegalArgumentException("Recycle percentage must be between 0 and 1");
        }

        calculation.setEnergyConsumption(calcUpdateRequestDTO.getEnergyConsumption());
        calculation.setTransportation(calcUpdateRequestDTO.getTransportation());
        calculation.setSolidWasteTotal(calcUpdateRequestDTO.getSolidWasteTotal());
        calculation.setRecyclePercentage(calcUpdateRequestDTO.getRecyclePercentage());

        calculation.setUpdatedAt(Instant.now());

        calculationRepository.save(calculation);

        logger.info("Calculation updated: {}", calculation.getId());

        return true;
    }

}
