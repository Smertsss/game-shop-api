import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { GameService } from '../../services/game.service';
import { Game } from '../../models/game.model';

@Component({
  selector: 'app-game-view',
  templateUrl: './game-view.component.html',
  styleUrls: ['./game-view.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class GameViewComponent implements OnInit {
  game: Game | null = null;
  currentImageIndex: number = 0;
  loading: boolean = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private gameService: GameService
  ) {}

  ngOnInit(): void {
    const gameId = this.route.snapshot.paramMap.get('id');
    if (gameId) {
      this.loadGame(gameId);
    } else {
      this.error = 'ID игры не указан';
      this.loading = false;
    }
  }

  loadGame(id: string): void {
    this.gameService.getGameById(id).subscribe({
      next: (game) => {
        if (!game || !game.id) {
          this.error = 'Игра не найдена';
          this.loading = false;
          return;
        }

        this.game = game;
        this.loading = false;
      },
      error: (err) => {
        if (err.status === 404) {
          this.error = 'Игра не найдена';
        } else {
          this.error = 'Не удалось загрузить данные игры';
        }
        this.loading = false;
        console.error('Ошибка загрузки игры:', err);
      }
    });
  }

  getImageUrl(imageName: string): string {
    return `http://localhost:8080/api/images/Game/${imageName}`;
  }

  // Получаем URL текущего изображения
  getCurrentImageUrl(): string {
    if (this.hasImages() && this.game!.images![this.currentImageIndex]) {
      return this.getImageUrl(this.game!.images![this.currentImageIndex]);
    }
    return '';
  }

  changeImage(index: number): void {
    if (this.hasImages() && index >= 0 && index < this.game!.images!.length) {
      this.currentImageIndex = index;
    }
  }

  calculateRating(): number {
    if (!this.game) return 0;

    const likes = this.game.likedByUsers?.length || 0;
    const dislikes = this.game.dislikedByUsers?.length || 0;
    const total = likes + dislikes;

    if (total === 0) return 0;

    const rating = (likes / total) * 5;
    return Math.round(rating * 2) / 2;
  }

  getStarsArray(): number[] {
    const rating = this.calculateRating();
    return Array(5).fill(0).map((_, i) => {
      if (rating >= i + 1) return 1;
      if (rating >= i + 0.5) return 0.5;
      return 0;
    });
  }

  hasImages(): boolean {
    return !!(this.game && this.game.images && this.game.images.length > 0);
  }

  safeGet<T>(value: T | undefined | null, defaultValue: T): T {
    return value !== undefined && value !== null ? value : defaultValue;
  }
}
