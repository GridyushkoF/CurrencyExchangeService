package ru.skillbox.currency.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.ResultSimpleDto;
import ru.skillbox.currency.exchange.service.CentralBankApiService;
import ru.skillbox.currency.exchange.service.CurrencyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CentralBankApiService centralBankService;

    @Autowired
    public CurrencyController(CentralBankApiService centralBankService, CurrencyService currencyService) {
        this.centralBankService = centralBankService;
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CurrencyDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(currencyService.getById(id));
    }

    @GetMapping(value = "/convert")
    ResponseEntity<Double> convertValue(@RequestParam("value") Long value, @RequestParam("numCode") Long numCode) {
        return ResponseEntity.ok(currencyService.convertValue(value, numCode));
    }

    @PostMapping("/create")
    ResponseEntity<CurrencyDto> create(@RequestBody CurrencyDto dto) {
        return ResponseEntity.ok(currencyService.create(dto));

    }

    @GetMapping("/")
    ResponseEntity<ResultSimpleDto> getAll() {
        try {
            centralBankService.fillCurrencyFromCentralBank();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(currencyService.getAll());
    }
}
