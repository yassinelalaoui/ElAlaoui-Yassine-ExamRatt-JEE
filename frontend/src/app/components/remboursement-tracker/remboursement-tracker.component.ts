import { Component } from '@angular/core';
import { RemboursementService } from '../../services/remboursement.service';
import { Remboursement } from '../../models/remboursement.model';

@Component({
  selector: 'app-remboursement-tracker',
  templateUrl: './remboursement-tracker.component.html',
  styleUrls: ['./remboursement-tracker.component.css']
})
export class RemboursementTrackerComponent {
  creditId = 1;
  remboursements: Remboursement[] = [];
  payment: Remboursement = { creditId: 1, montant: 0, type: 'MENSUALITE' };
  message = '';

  constructor(private paymentService: RemboursementService) {}

  loadPayments(): void {
    this.paymentService.getPayments(this.creditId).subscribe(payments => this.remboursements = payments);
  }

  addPayment(): void {
    this.payment.creditId = this.creditId;
    this.paymentService.addPayment(this.payment).subscribe({
      next: () => {
        this.message = 'Paiement enregistré.';
        this.payment = { creditId: this.creditId, montant: 0, type: 'MENSUALITE' };
        this.loadPayments();
      },
      error: () => this.message = 'Erreur lors de l’enregistrement du paiement.'
    });
  }
}
