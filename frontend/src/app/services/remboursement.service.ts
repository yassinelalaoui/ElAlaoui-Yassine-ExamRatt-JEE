import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Remboursement } from '../models/remboursement.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class RemboursementService {
  private baseUrl = `${environment.apiUrl}/remboursements`;

  constructor(private http: HttpClient) {}

  getPayments(creditId: number): Observable<Remboursement[]> {
    return this.http.get<Remboursement[]>(`${this.baseUrl}/credit/${creditId}`);
  }

  addPayment(remboursement: Remboursement): Observable<Remboursement> {
    return this.http.post<Remboursement>(`${this.baseUrl}/credit/${remboursement.creditId}`, remboursement);
  }
}
