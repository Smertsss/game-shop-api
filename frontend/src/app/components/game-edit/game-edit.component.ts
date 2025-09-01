import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { GameService } from '../../services/game.service';
import { GameUpdateDto } from '../../models/game.model';

@Component({
  selector: 'app-game-edit',
  templateUrl: './game-edit.component.html',
  styleUrls: ['./game-edit.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class GameEditComponent implements OnInit {
  game: any = {
    name: '',
    context: '',
    cost: 0,
    images: [],
    genres: []
  };

  gameId: string = '';
  isLoading = true;
  isSaving = false;
  error: string | null = null;
  selectedFile: File | null = null;
  previewImage: string | null = null;

  // Для работы с жанрами
  allGenres: any[] = [];
  selectedGenreId: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService
  ) {}

  ngOnInit() {
    this.gameId = this.route.snapshot.paramMap.get('id') || '';
    if (this.gameId) {
      this.loadGame(this.gameId);
      this.loadAllGenres();
    } else {
      this.error = 'ID игры не указан';
      this.isLoading = false;
    }
  }

  loadGame(id: string) {
    console.log('Loading game with ID:', id);
    this.gameService.getGameById(id).pipe(
      catchError(error => {
        console.error('Error loading game:', error);
        this.error = 'Ошибка загрузки игры';
        this.isLoading = false;
        return of(null);
      })
    ).subscribe(game => {
      if (game) {
        console.log('Game loaded:', game);
        console.log('Game images:', game.images);
        console.log('Game genres:', game.genres);

        this.game = {
          ...game,
          images: game.images || [],
          genres: game.genres || []
        };

        console.log('Processed game:', this.game);
      } else {
        console.error('Game is null');
      }
      this.isLoading = false;
    });
  }

  loadAllGenres() {
    this.gameService.getAllGenres().pipe(
      catchError(error => {
        console.error('Error loading genres:', error);
        return of([]);
      })
    ).subscribe(genres => {
      this.allGenres = genres;
    });
  }

  onFileSelected(event: any) {
    console.log('File selected event:', event);
    const file = event.target.files[0];
    console.log('Selected file:', file);
    if (file) {
      this.selectedFile = file;
      console.log('File set to selectedFile');

      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.previewImage = e.target.result;
        console.log('Preview image set');
      };
      reader.readAsDataURL(file);
    }
  }

  uploadImage() {
    console.log('uploadImage() called');
    console.log('Selected file:', this.selectedFile);
    console.log('Game ID:', this.gameId);

    if (this.selectedFile && this.gameId) {
      console.log('Начинаем загрузку файла:', this.selectedFile.name);
      console.log('Размер файла:', this.selectedFile.size, 'bytes');
      console.log('Тип файла:', this.selectedFile.type);

      this.isLoading = true;

      this.gameService.uploadGameImage(this.gameId, this.selectedFile).subscribe({
        next: (imageName) => {
          console.log('Изображение успешно загружено:', imageName);
          this.loadGame(this.gameId);
          this.selectedFile = null;
          this.previewImage = null;
        },
        error: (error) => {
          console.error('Error uploading image:', error);
          console.error('Error details:', error.error);
          this.error = 'Ошибка загрузки изображения: ' + error.message;
          this.isLoading = false;
        }
      });
    } else {
      console.error('Недостаточно данных для загрузки:');
      console.error('selectedFile:', this.selectedFile);
      console.error('gameId:', this.gameId);
    }
  }

  deleteImage(imageName: string) {
    if (this.gameId) {
      if (confirm('Удалить это изображение?')) {
        this.isLoading = true;
        this.gameService.deleteGameImage(this.gameId, imageName).subscribe({
          next: () => {
            // Обновляем локальный список изображений
            this.game.images = this.game.images.filter((img: string) => img !== imageName);
            this.isLoading = false;
          },
          error: (error) => {
            console.error('Error deleting image:', error);
            this.error = 'Ошибка удаления изображения';
            this.isLoading = false;
          }
        });
      }
    }
  }

  addGenre() {
    if (this.selectedGenreId && !this.game.genres.some((g: any) => g.id === this.selectedGenreId)) {
      this.isLoading = true;
      this.gameService.addGenresToGame(this.gameId, [this.selectedGenreId]).subscribe({
        next: (updatedGame) => {
          this.game.genres = updatedGame.genres || [];
          this.selectedGenreId = '';
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error adding genre:', error);
          this.error = 'Ошибка добавления жанра';
          this.isLoading = false;
        }
      });
    }
  }

  removeGenre(genreId: string) {
    if (confirm('Удалить этот жанр из игры?')) {
      this.isLoading = true;
      this.gameService.removeGenreFromGame(this.gameId, genreId).subscribe({
        next: (updatedGame) => {
          this.game.genres = updatedGame.genres || [];
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error removing genre:', error);
          this.error = 'Ошибка удаления жанра';
          this.isLoading = false;
        }
      });
    }
  }

  onSubmit() {
    if (!this.gameId) {
      this.error = 'ID игры не указан';
      return;
    }

    this.isSaving = true;
    this.error = null;

    const updateData: GameUpdateDto = {
      id: this.gameId,
      name: this.game.name,
      context: this.game.context,
      cost: this.game.cost
    };

    this.gameService.updateGame(this.gameId, updateData).subscribe({
      next: () => {
        this.router.navigate(['/games']);
      },
      error: (error) => {
        this.error = 'Ошибка сохранения: ' + (error.error?.message || error.message || 'Неизвестная ошибка');
        this.isSaving = false;
        console.error('Update error:', error);
      }
    });
  }

  cancel() {
    this.router.navigate(['/games']);
  }

  getImageUrl(imageName: string): string {
    return `http://localhost:8080/api/images/Game/${imageName}`;
  }

  handleImageError(event: any) {
    console.error('Ошибка загрузки изображения', event);
    event.target.style.display = 'none';
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';

    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }

  // Безопасный доступ к свойствам (как в game-view)
  safeGet<T>(value: T | undefined | null, defaultValue: T): T {
    return value !== undefined && value !== null ? value : defaultValue;
  }
}
