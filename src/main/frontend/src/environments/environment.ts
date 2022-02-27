// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const authServerAPIUrl = 'http://host.docker.internal:30010'
export const environment = {
    production: false,
    baseAPIUrl: 'https://currency-exchange-assessment.firebaseapp.com',
    exchangeRatesAPIUrl: 'https://api.exchangerate.host',
    authServerAPIUrl: authServerAPIUrl,
    backendServiceURL: 'http://localhost:8080/bcp/currency-exchange',
    openIdURL: `${authServerAPIUrl}/auth/realms/eureka/protocol/openid-connect/auth?response_type=code&client_id=eureka-cli&scope=profile&redirect_uri=http://localhost:4200/authorized`
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
