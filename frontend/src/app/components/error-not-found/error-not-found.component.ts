import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error-not-found',
  templateUrl: './error-not-found.component.html',
  styleUrls: ['./error-not-found.component.css']
})
export class ErrorNotFoundComponent {

  constructor(private router: Router) {}

  goHome(): void {
    this.router.navigate(['/main-games']);
  }
}
