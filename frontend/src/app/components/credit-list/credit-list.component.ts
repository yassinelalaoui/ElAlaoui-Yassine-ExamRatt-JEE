import { Component, OnInit } from '@angular/core';
import { CreditService } from '../../services/credit.service';
import { Credit } from '../../models/credit.model';

@Component({
  selector: 'app-credit-list',
  templateUrl: './credit-list.component.html',
  styleUrls: ['./credit-list.component.css']
})
export class CreditListComponent implements OnInit {
  credits: Credit[] = [];
  clientId = 1;
  selectedCreditId: number | null = null;

  constructor(private creditService: CreditService) {}

  ngOnInit(): void {
    this.loadCredits();
  }

  loadCredits(): void {
    this.creditService.getCredits(this.clientId).subscribe(credits => this.credits = credits);
  }

  selectCredit(id: number): void {
    this.selectedCreditId = id;
  }
}
