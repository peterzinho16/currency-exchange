const authServerAPIUrl = 'http://host.docker.internal:30010'

export const environment = {
    production: true,
    baseAPIUrl: 'http://localhost:4200',
    exchangeRatesAPIUrl: 'https://api.exchangerate.host',
    authServerAPIUrl: authServerAPIUrl,
    backendServiceURL: 'http://localhost:8080/bcp/currency-exchange',
    openIdURL: `${authServerAPIUrl}/auth/realms/eureka/protocol/openid-connect/auth?response_type=code&client_id=eureka-cli&scope=profile&redirect_uri=http://localhost:4200/authorized`
};
