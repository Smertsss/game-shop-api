import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AuthService } from './authentication/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
  imports: [
    RouterOutlet,
    FormsModule,
    NavbarComponent
    ]
})
export class AppComponent {
  title = 'frontend';

  constructor(public router: Router, private authService: AuthService) {}

  showNavbar(): boolean {
    return !this.router.url.includes('/login');
  }

  ngOnInit() {
    this.authService.checkAuthStatus();
  }
}
