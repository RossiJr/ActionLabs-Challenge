package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnergyEmissionFactorService {
    @Autowired
    private EnergyEmissionFactorRepository energyEmissionFactorRepository;

    private double getEnergyEmissionFactor(UF uf) {
        return energyEmissionFactorRepository.findById(uf.toString()).get().getFactor();
    }

    public double calculateCarbonEmission(int energyConsumption, UF uf) {
        return energyConsumption * getEnergyEmissionFactor(uf);
    }
}
