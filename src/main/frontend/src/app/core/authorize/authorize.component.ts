import { Component, OnInit } from '@angular/core';
import { combineLatest } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthServerApiRequestService } from 'src/app/shared/service/auth-server-api-request.service';
import { AuthServerResponse } from 'src/app/shared/interface/exchange-rates.model';
import { StorageService } from '../../shared/service/storage.service';
import { environment } from 'src/environments/environment';

@Component({
    selector: 'app-authorize',
    templateUrl: './authorize.component.html',
    styleUrls: ['./authorize.component.scss'],
})
export class AuthorizeComponent implements OnInit {

    constructor(
        public router: Router,
        private route: ActivatedRoute,
        private authServerService: AuthServerApiRequestService) {}

    ngOnInit() {
        this.getAuthorizeJWT(this.route.snapshot.queryParams['code']);
    }

    getAuthorizeJWT(code: string): void {
        this.authServerService.getAuthServer(code).subscribe(
            (authResponse: AuthServerResponse): void => {
                StorageService.setItem('JWT_BACKEND', authResponse.access_token);
                StorageService.setItem('JWT_EXPIRATION_TIME', authResponse.expires_in.toString())
                this.router.navigate(['converter']);
            },
            (error): void => {
                console.log('FAILED getAuthorizeJWT(code: string) method');
                window.location.href = environment.openIdURL;
            },
        );
        
    }
}
