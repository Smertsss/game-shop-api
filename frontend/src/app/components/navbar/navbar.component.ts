import { Component } from '@angular/core';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../authentication/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
    ]
})
export class NavbarComponent {
  currentUserRole: string = 'USER';

  constructor(
    public router: Router,
    public authService: AuthService
    ) {}

  isAdmin(): boolean {
    return this.currentUserRole === 'ADMIN';
  }

  isUser(): boolean {
    return this.currentUserRole === 'USER';
  }

  getAuthenticated(): string {
      return this.authService.isAuthenticated() ? '/home' : '/';
  }

  logout(): void {
      this.authService.logout().subscribe({
          next: () => {
              console.log('Logout successful');
          },
          error: (err: Error) => {
              console.error('Logout failed:', err);
          }
      });
  }
}
