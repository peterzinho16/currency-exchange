export interface StringNumberPair {
    [key: string]: number;
}

export interface ExchangeRatesResponse {
    base: string;
    rates: StringNumberPair;
}

export interface CurrencyResponse {
    id: number,
    name: string;
    code: string;
}

export interface ExchangeRateResponse {
    id: number,
    sourceId: number;
    destinyId: number;
    sourceUnit: number;
    destinyRate: number;
    creationDate: Date;
    enabled: boolean
}

export interface MappedCurrencyRateObject {
    id: number,
    currency: string;
    rate: number;
}

export interface Statistics {
    name: string;
    summary: number;
}

export interface AuthServerResponse {
    access_token: string;
    expires_in: number;
    refresh_expires_in: number;
    refresh_token: string;
    scope: string;
}
