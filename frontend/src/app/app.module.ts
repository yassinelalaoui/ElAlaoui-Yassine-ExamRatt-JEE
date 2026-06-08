import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ClientManagementComponent } from './components/client-management/client-management.component';
import { CreditApplicationComponent } from './components/credit-application/credit-application.component';
import { CreditListComponent } from './components/credit-list/credit-list.component';
import { RemboursementTrackerComponent } from './components/remboursement-tracker/remboursement-tracker.component';
import { AuthInterceptor } from './auth/auth.interceptor';
import { AuthGuard } from './auth/auth.guard';
import { RoleGuard } from './auth/role.guard';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    DashboardComponent,
    ClientManagementComponent,
    CreditApplicationComponent,
    CreditListComponent,
    RemboursementTrackerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: DashboardComponent, canActivate: [AuthGuard] },
      { path: 'login', component: LoginComponent },
      { path: 'clients', component: ClientManagementComponent, canActivate: [AuthGuard, RoleGuard], data: { roles: ['ROLE_ADMIN', 'ROLE_EMPLOYE'] } },
      { path: 'credits', component: CreditListComponent, canActivate: [AuthGuard] },
      { path: 'apply-credit', component: CreditApplicationComponent, canActivate: [AuthGuard, RoleGuard], data: { roles: ['ROLE_CLIENT'] } },
      { path: 'payments', component: RemboursementTrackerComponent, canActivate: [AuthGuard] },
      { path: '**', redirectTo: '' }
    ])
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
