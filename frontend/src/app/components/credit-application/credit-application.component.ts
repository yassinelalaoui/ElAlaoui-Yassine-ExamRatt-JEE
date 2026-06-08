import { Component } from '@angular/core';
import { CreditService } from '../../services/credit.service';
import { Credit } from '../../models/credit.model';

@Component({
  selector: 'app-credit-application',
  templateUrl: './credit-application.component.html',
  styleUrls: ['./credit-application.component.css']
})
export class CreditApplicationComponent {
  credit: Credit = {
    clientId: 1,
    montant: 0,
    dureeRemboursement: 0,
    tauxInteret: 0,
    creditType: 'PERSONNEL'
  };
  message = '';

  constructor(private creditService: CreditService) {}

  submit(): void {
    this.creditService.applyCredit(this.credit).subscribe({
      next: () => this.message = 'Demande de crédit enregistrée avec succès.',
      error: () => this.message = 'Une erreur est survenue lors de l’enregistrement.'
    });
  }
}
