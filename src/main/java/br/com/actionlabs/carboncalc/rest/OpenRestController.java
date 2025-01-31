package br.com.actionlabs.carboncalc.rest;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.service.CalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
@Slf4j
public class OpenRestController {
    @Autowired
    private CalculationService calculationService;

    @PostMapping("start-calc")
    public ResponseEntity<StartCalcResponseDTO> startCalculation(
            @RequestBody StartCalcRequestDTO request) {

        return ResponseEntity.ok(calculationService.startCalc(request));
    }

    @PutMapping("info")
    public ResponseEntity<UpdateCalcInfoResponseDTO> updateInfo(
            @RequestBody UpdateCalcInfoRequestDTO request) {

        return ResponseEntity.ok(new UpdateCalcInfoResponseDTO(calculationService.updateCalcInfo(request)));
    }

    @GetMapping("result/{id}")
    public ResponseEntity<CarbonCalculationResultDTO> getResult(@PathVariable String id) {

        return ResponseEntity.ok(calculationService.getCarbonFootprint(id));
    }
}
