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

    public double calculateCarbonEmission(double distance, TransportationType type) {
        return distance * getTransportationEmissionFactor(type);
    }

}
