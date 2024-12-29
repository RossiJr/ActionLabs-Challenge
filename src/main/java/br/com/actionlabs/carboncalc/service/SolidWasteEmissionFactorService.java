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


    public double calculateCarbonEmission(int solidWaste, double recyclePercentage, UF uf){
        SolidWasteEmissionFactor solidWasteEmissionFactor = getSolidWasteEmissionFactor(uf);

        double recyclabeWaste = (solidWaste * recyclePercentage) * solidWasteEmissionFactor.getRecyclableFactor();
        double nonRecyclabeWaste = (solidWaste * (1 - recyclePercentage)) * solidWasteEmissionFactor.getNonRecyclableFactor();

        return recyclabeWaste + nonRecyclabeWaste;
    }

}
