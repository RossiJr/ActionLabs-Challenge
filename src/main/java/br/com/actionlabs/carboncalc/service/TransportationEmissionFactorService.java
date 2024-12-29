package br.com.actionlabs.carboncalc.service;


import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportationEmissionFactorService {
    @Autowired
    private TransportationEmissionFactorRepository transportationEmissionFactorRepository;

    private double getTransportationEmissionFactor(TransportationType type) {
        return transportationEmissionFactorRepository.findById(type).get().getFactor();
    }


    /**
     * Calculates the carbon emission based on the distance and the type's transportation emission factor.</br>
     * The formula is: Carbon Emission = Distance * Transportation Emission Factor
     *
     * @param distance the distance
     * @param type the transportation type
     * @return the carbon emission
     */
    public double calculateCarbonEmission(double distance, TransportationType type) {
        return distance * getTransportationEmissionFactor(type);
    }

}
