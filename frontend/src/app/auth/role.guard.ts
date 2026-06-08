import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const roles = route.data['roles'] as Array<string>;
    const userRole = this.authService.getRole();
    if (!userRole || !roles.includes(userRole)) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
