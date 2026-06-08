import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Credit } from '../models/credit.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CreditService {
  private baseUrl = `${environment.apiUrl}/credits`;

  constructor(private http: HttpClient) {}

  getCredits(clientId: number): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.baseUrl}/client/${clientId}`);
  }

  applyCredit(credit: Credit): Observable<Credit> {
    return this.http.post<Credit>(this.baseUrl, credit);
  }

  approveCredit(creditId: number, statut: string): Observable<Credit> {
    return this.http.put<Credit>(`${this.baseUrl}/${creditId}/approve?statut=${statut}`, {});
  }

  getRemainingBalance(creditId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/${creditId}/balance`);
  }

  getTotalPaid(creditId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/${creditId}/paid`);
  }
}
