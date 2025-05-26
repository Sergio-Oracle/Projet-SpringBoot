// src/app/dashboard/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { LivreService } from '../services/livre.service';
import { EmpruntService } from '../services/emprunt.service';
import { Livre } from '../models/livre';
import { Emprunt } from '../models/emprunt';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  livres: Livre[] = [];
  emprunts: Emprunt[] = [];
  errorMessage = '';
  newLivre: Livre = { id: 0, titre: '', auteur: '', disponible: true };
  newEmprunt: Emprunt = { id: 0, idUtilisateur: 0, idLivre: 0, dateEmprunt: '', dateRetour: null };

  constructor(
    private authService: AuthService,
    private livreService: LivreService,
    private empruntService: EmpruntService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadLivres();
    const userId = 1; // Remplacer par l'ID utilisateur réel
    this.newEmprunt.idUtilisateur = userId;
    this.loadEmprunts(userId);
  }

  loadLivres() {
    this.livreService.getLivres().subscribe({
      next: (livres: Livre[]) => this.livres = livres,
      error: (err: any) => this.errorMessage = err.message
    });
  }

  loadEmprunts(userId: number) {
    this.empruntService.getEmpruntsByUser(userId).subscribe({
      next: (emprunts: Emprunt[]) => this.emprunts = emprunts,
      error: (err: any) => this.errorMessage = err.message
    });
  }

  createLivre() {
    this.livreService.createLivre(this.newLivre).subscribe({
      next: (livre: Livre) => {
        this.livres.push(livre);
        this.newLivre = { id: 0, titre: '', auteur: '', disponible: true };
      },
      error: (err: any) => this.errorMessage = err.message
    });
  }

  createEmprunt() {
    this.empruntService.createEmprunt(this.newEmprunt).subscribe({
      next: (emprunt: Emprunt) => {
        this.emprunts.push(emprunt);
        this.loadLivres();
        this.newEmprunt = { id: 0, idUtilisateur: this.newEmprunt.idUtilisateur, idLivre: 0, dateEmprunt: '', dateRetour: null };
      },
      error: (err: any) => this.errorMessage = err.message
    });
  }

  retournerEmprunt(id: number) {
    this.empruntService.retournerEmprunt(id).subscribe({
      next: (emprunt: Emprunt) => {
        const index = this.emprunts.findIndex(e => e.id === id);
        this.emprunts[index] = emprunt;
        this.loadLivres();
      },
      error: (err: any) => this.errorMessage = err.message
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // Nouvelle méthode pour obtenir le titre du livre
  getLivreTitre(idLivre: number): string {
    const livre = this.livres.find(l => l.id === idLivre);
    return livre ? livre.titre : 'Livre non trouvé';
  }
}