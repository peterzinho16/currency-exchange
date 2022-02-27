import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { AuthService } from './auth.service';

@Injectable()
export class AuthGuardService implements CanActivate {
    constructor(private router: Router, private authService: AuthService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (this.authService.isAuthenticatedJWT()) {
            return true;
        } else {
            window.location.href = 'http://localhost:30010' + 
                                    '/auth/realms/eureka/protocol/openid-connect/auth'+
                                    '?response_type=code&client_id=eureka-cli&scope=profile&redirect_uri=http://localhost:4200/authorized'
            return false;
        }
    }
}
