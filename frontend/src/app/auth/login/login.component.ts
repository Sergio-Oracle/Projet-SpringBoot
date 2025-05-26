// src/app/auth/login/login.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = { email: '', password: '' };
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {
    console.log('LoginComponent chargé');
  }

  onSubmit() {
    console.log('Tentative de connexion avec:', this.loginData);
    this.errorMessage = ''; // Réinitialiser le message d'erreur
    this.authService.login(this.loginData).subscribe({
      next: (response: { token: string }) => {
        this.authService.saveToken(response.token);
        console.log('Connexion réussie, redirection vers /dashboard');
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        this.errorMessage = err.message;
        console.error('Erreur dans onSubmit:', err);
      }
    });
  }
}