import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.prod';
import { AuthServerResponse } from '../interface/exchange-rates.model';

@Injectable()
export class AuthServerApiRequestService {
    constructor(public http: HttpClient) {}

    public getAuthServer(code: string): Observable<AuthServerResponse> {
        const headers = new HttpHeaders()
                                .set('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');

            const params =
                new HttpParams({
                    fromObject: {
                        'grant_type': 'authorization_code',
                        'client_id': 'eureka-cli',
                        'client_secret': 'cf81db6a-a50e-4b51-b7f2-704959f99d8f',
                        'redirect_uri': window.location.href.split("?")[0],
                        'code': code
                    }
                });
        return this.http.post<AuthServerResponse>(`${environment.authServerAPIUrl}/auth/realms/eureka/protocol/openid-connect/token`, params, {headers});
    }
}
