import { Component } from '@angular/core';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

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

  constructor(public router: Router) {}

  isAdmin(): boolean {
    return this.currentUserRole === 'ADMIN';
  }

  isUser(): boolean {
    return this.currentUserRole === 'USER';
  }
}
