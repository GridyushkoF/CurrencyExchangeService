package ru.skillbox.currency.exchange.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;
import ru.skillbox.currency.exchange.xmlStructure.ValCursXml;
import ru.skillbox.currency.exchange.xmlStructure.ValuteXml;

import java.net.URL;
import java.util.Optional;

@Setter
@Slf4j
@Service
@EnableScheduling
public class CentralBankApiService {
    @Value("${cbr.daily}")
    private String linkToDailyXml;

    @Autowired
    public CentralBankApiService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    private CurrencyRepository currencyRepository;
    private static final long ONE_HOUR_IN_MILLIS = 3_600_000;

    public ValCursXml getValCursFromCentralBank() {
        try {
            JAXBContext context = JAXBContext.newInstance(ValCursXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ValCursXml) unmarshaller.unmarshal(new URL(linkToDailyXml));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void fillCurrencyFromCentralBank() {
        try {
            ValCursXml cbrData = getValCursFromCentralBank();
            cbrData.getValuteXmls().forEach(valuteXml -> {
                Optional<Currency> currencyOpt = currencyRepository.findByIsoLiteralCode(valuteXml.getCharCode());
                Currency currency;
                currency = currencyOpt.orElseGet(Currency::new);
                setXmlPropertiesToCurrency(valuteXml, currency);
                currencyRepository.save(currency);
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void setXmlPropertiesToCurrency(ValuteXml valuteXml, Currency currency) {
        currency.setName(valuteXml.getName());
        currency.setValue(Double.valueOf(valuteXml.getValue().replaceAll(",", ".")));
        currency.setNominal(valuteXml.getNominal());
        currency.setIsoNumCode(Long.valueOf(valuteXml.getNumCode()));
        currency.setIsoLiteralCode(valuteXml.getCharCode());
    }

    //every hour work!
    @Scheduled(fixedRate = ONE_HOUR_IN_MILLIS)
    public void updateEveryHour() {
        fillCurrencyFromCentralBank();
    }
}
