package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.dto.StartCalcResponseDTO;
import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.model.Calculation;
import br.com.actionlabs.carboncalc.repository.CalculationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;

@Service
public class CalculationService {
    @Autowired
    private CalculationRepository calculationRepository;

    private static final Logger logger = LoggerFactory.getLogger(CalculationService.class);


    /**
     * Starts a new calculation, validating if the required fields are present
     * @param calcRequestDTO the request with the initial required data
     * @return the response with the calculation id
     */
    public StartCalcResponseDTO startCalc(StartCalcRequestDTO calcRequestDTO){
        if (calcRequestDTO == null){
            throw new IllegalArgumentException("Calculation cannot be null");
        }

        if (calcRequestDTO.getName() == null || calcRequestDTO.getName().isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (calcRequestDTO.getEmail() == null || calcRequestDTO.getEmail().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (calcRequestDTO.getUf() == null || calcRequestDTO.getUf().isEmpty() ||
                UF.fromString(calcRequestDTO.getUf()) == null){
            throw new IllegalArgumentException("UF must exist");
        }

        // A stronger validation can be implemented in further versions
        if (calcRequestDTO.getPhoneNumber() == null || calcRequestDTO.getPhoneNumber().isEmpty()){
            throw new IllegalArgumentException("Phone number cannot be null or empty");
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

}
