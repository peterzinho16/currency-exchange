package com.example.currencyexchange.mapper;

import com.example.currencyexchange.controller.api.ExchangeRatePatchResource;
import com.example.currencyexchange.controller.api.NewExchangeRateResource;
import com.example.currencyexchange.domain.ExchangeRate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class ExchangeRateMapper {

    public static final String CREATED_BY ="peterpaul16";

    public ExchangeRate toModel(NewExchangeRateResource itemResource) {
        var exchangeRate = new ExchangeRate();
        exchangeRate.setSourceId(itemResource.getSourceId());
        exchangeRate.setDestinyId(itemResource.getDestinyId());
        exchangeRate.setDestinyRate(itemResource.getDestinyRate());
        exchangeRate.setSourceUnit(itemResource.getSourceUnit());
        exchangeRate.setEnabled(itemResource.getEnabled());
        exchangeRate.setCreatedBy(CREATED_BY);
        return exchangeRate;
    }

    public ExchangeRate patch(ExchangeRatePatchResource patchResource, ExchangeRate exchangeRate) {
        if (patchResource.getDestinyRate() != null && patchResource.getDestinyRate().isPresent()) {
            exchangeRate.setDestinyRate(
                    Double.parseDouble(patchResource.getDestinyRate().get()));
        }

        if (patchResource.getEnabled() != null && patchResource.getEnabled().isPresent()) {
            exchangeRate.setEnabled(patchResource.getEnabled().get());
        }

        return exchangeRate;
    }

}
