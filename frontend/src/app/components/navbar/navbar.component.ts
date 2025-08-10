import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../authentication/auth.service';
import { UserPreview } from '../../models/user.model';

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
export class NavbarComponent implements OnInit {
  currentUser: UserPreview | null = null;

  constructor(
    public router: Router,
    public authService: AuthService
    ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      console.log('Current user in navbar:', user);
      console.log('Current roles:', user?.roles);
    });
  }

  hasRole(roleName: string): boolean {
    return this.authService.hasRole(roleName);
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
