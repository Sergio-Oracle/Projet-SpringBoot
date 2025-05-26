import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Emprunt } from '../models/emprunt';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class EmpruntService {
  private apiUrl = 'http://localhost:8080/emprunts';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getEmpruntsByUser(userId: number): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(`${this.apiUrl}/utilisateur/${userId}`, { headers: this.getHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  createEmprunt(emprunt: Emprunt): Observable<Emprunt> {
    return this.http.post<Emprunt>(this.apiUrl, emprunt, { headers: this.getHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  retournerEmprunt(id: number): Observable<Emprunt> {
    return this.http.post<Emprunt>(`${this.apiUrl}/retour/${id}`, {}, { headers: this.getHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    });
  }

  private handleError(error: any) {
    let errorMessage = 'Une erreur est survenue lors de l\'emprunt. Veuillez réessayer.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erreur: ${error.error.message}`;
    } else {
      errorMessage = `Erreur ${error.status}: ${error.error.message || error.statusText}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
