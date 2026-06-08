export interface Credit {
  id?: number;
  dateDemande?: string;
  statut?: string;
  dateAcceptation?: string;
  montant: number;
  dureeRemboursement: number;
  tauxInteret: number;
  clientId: number;
  creditType: string;
  motif?: string;
  typeBien?: string;
  raisonSociale?: string;
  totalPayee?: number;
  remainingBalance?: number;
}
