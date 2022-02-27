import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment.prod';
import { CurrencyResponse, ExchangeRateResponse, ExchangeRatesResponse } from '../interface/exchange-rates.model';
import { StorageService } from './storage.service';

@Injectable()
export class ExchangeRatesApiRequestService {
    constructor(public http: HttpClient) {}

    public getExchangeRates(baseCurrency: string): Observable<ExchangeRatesResponse> {
        return this.http.get<ExchangeRatesResponse>(`${environment.exchangeRatesAPIUrl}/latest?base=${baseCurrency}`);
    }

    public getExchangeRatesLocal(): Observable<CurrencyResponse[]> {
        const params =
                new HttpParams({
                    fromObject: {
                    }
                });
        const headers = new HttpHeaders()
                                .set('Authorization', `Bearer ${StorageService.getItem('JWT_BACKEND')}`)
                                .set('Content-Type', 'application/json; charset=UTF-8');
        return this.http.get<CurrencyResponse[]>(`${environment.backendServiceURL}/currency`, {headers});
    }

    public getExchangeRatesToCompute(): Observable<ExchangeRateResponse[]> {
        const params =
                new HttpParams({
                    fromObject: {
                    }
                });
        const headers = new HttpHeaders()
                                .set('Authorization', `Bearer ${StorageService.getItem('JWT_BACKEND')}`)
                                .set('Content-Type', 'application/json; charset=UTF-8');
        return this.http.get<ExchangeRateResponse[]>(`${environment.backendServiceURL}/exchange-rate`, {headers});
    }
}
