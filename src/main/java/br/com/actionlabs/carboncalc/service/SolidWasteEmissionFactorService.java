package br.com.actionlabs.carboncalc.service;

import br.com.actionlabs.carboncalc.enums.UF;
import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolidWasteEmissionFactorService {
    @Autowired
    private SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;

    private SolidWasteEmissionFactor getSolidWasteEmissionFactor(UF uf){
        return solidWasteEmissionFactorRepository.findById(uf.toString()).get();
    }


    /**
     * Calculates the carbon emission from solid waste,
     * based on the recyclable and non-recyclable factors for each UF and percentage. </br>
     * The formula is: Carbon Emission =
     *   (Solid Waste * Recycle Percentage * Recyclable Factor) +
     *   (Solid Waste * (1 - Recycle Percentage) * Non-Recyclable Factor)
     * @param solidWaste the provided solid waste
     * @param recyclePercentage the recycle percentage (0 to 1)
     * @param uf the UF
     * @return the carbon emission
     */
    public double calculateCarbonEmission(int solidWaste, double recyclePercentage, UF uf){
        SolidWasteEmissionFactor solidWasteEmissionFactor = getSolidWasteEmissionFactor(uf);

        double recyclabeWaste = (solidWaste * recyclePercentage) * solidWasteEmissionFactor.getRecyclableFactor();
        double nonRecyclabeWaste = (solidWaste * (1 - recyclePercentage)) * solidWasteEmissionFactor.getNonRecyclableFactor();

        return recyclabeWaste + nonRecyclabeWaste;
    }

}
