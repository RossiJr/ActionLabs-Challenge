package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import br.com.actionlabs.carboncalc.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnergyEmissionFactorService {
    @Autowired
    private EnergyEmissionFactorRepository energyEmissionFactorRepository;

    private double getEnergyEmissionFactor(UF uf) {
        return energyEmissionFactorRepository.findById(uf.toString()).get().getFactor();
    }

    /**
     * Calculates the carbon emission based on the energy consumption and the UF's energy emission factor.</br>
     * The formula is: Carbon Emission = Energy Consumption * Energy Emission Factor
     * </br>
     * The result is rounded to 6 decimal places to avoid double precision errors.
     * @param energyConsumption the energy consumption
     * @param uf the UF
     * @return the carbon emission (rounded to 6 decimal places)
     */
    public double calculateCarbonEmission(int energyConsumption, UF uf) {
        return Utils.round(energyConsumption * getEnergyEmissionFactor(uf), 6);
    }
}
