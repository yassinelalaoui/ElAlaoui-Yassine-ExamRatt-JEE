import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-client-management',
  templateUrl: './client-management.component.html',
  styleUrls: ['./client-management.component.css']
})
export class ClientManagementComponent implements OnInit {
  clients: Client[] = [];
  newClient: Client = { nom: '', email: '' };
  message = '';

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.clientService.getClients().subscribe(clients => this.clients = clients);
  }

  addClient(): void {
    this.clientService.addClient(this.newClient).subscribe(client => {
      this.clients.push(client);
      this.newClient = { nom: '', email: '' };
      this.message = 'Client créé avec succès.';
    });
  }
}
