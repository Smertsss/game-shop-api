import { Component, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error-not-found',
  templateUrl: './error-not-found.component.html',
  styleUrls: ['./error-not-found.component.css']
})
export class ErrorNotFoundComponent {
  @ViewChild('particlesContainer') particlesContainer!: ElementRef;

  constructor(private router: Router) {}

  ngAfterViewInit(): void {
    this.createParticles();
  }

  goHome(): void {
    this.router.navigate(['/main-games']);
  }

  private createParticles(): void {
    const container = this.particlesContainer.nativeElement;
    const particleCount = 50;

    for (let i = 0; i < particleCount; i++) {
      const particle = document.createElement('div');
      particle.className = 'particle';

      const size = Math.random() * 5 + 2;
      const posX = Math.random() * 100;
      const delay = Math.random() * 6;
      const duration = Math.random() * 4 + 6;

      particle.style.width = `${size}px`;
      particle.style.height = `${size}px`;
      particle.style.left = `${posX}%`;
      particle.style.animationDelay = `${delay}s`;
      particle.style.animationDuration = `${duration}s`;

      const colors = ['#3394BE', '#00A3FF', '#FFFFFF', '#FF6B6B'];
      const randomColor = colors[Math.floor(Math.random() * colors.length)];
      particle.style.background = randomColor;

      container.appendChild(particle);
    }
  }
}
