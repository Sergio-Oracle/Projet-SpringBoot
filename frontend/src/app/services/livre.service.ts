import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Livre } from '../models/livre';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LivreService {
  private apiUrl = 'http://localhost:8080/livres';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getLivres(): Observable<Livre[]> {
    return this.http.get<Livre[]>(this.apiUrl, { headers: this.getHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  createLivre(livre: Livre): Observable<Livre> {
    return this.http.post<Livre>(this.apiUrl, livre, { headers: this.getHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  updateLivre(livre: Livre): Observable<Livre> {
    return this.http.put<Livre>(`${this.apiUrl}/${livre.id}`, livre, { headers: this.getHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  deleteLivre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() }).pipe(
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
    let errorMessage = 'Une erreur est survenue. Veuillez réessayer.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erreur: ${error.error.message}`;
    } else {
      errorMessage = `Erreur ${error.status}: ${error.error.message || error.statusText}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
