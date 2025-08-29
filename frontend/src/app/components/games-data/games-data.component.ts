import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-games-data',
  templateUrl: './games-data.component.html',
  styleUrl: './games-data.component.css',
  standalone: true,
  imports: [
    FormsModule,
    DataTableComponent,
    CommonModule
  ]
})
export class GamesDataComponent {
  gamesData: any[] = [];
  columns: string[] = ['id', 'name', 'context', 'cost', 'creationDate', 'updateDate'];
  isLoading = true;
  error: string | null = null;

  constructor(
    private dataService: DataTableService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadGames();
  }

  loadGames() {
    this.dataService.getGames().pipe(
      tap(data => {
        this.gamesData = data.map(game => ({
          ...game,
          creationDate: new Date(game.creationDate).toLocaleDateString(),
          updateDate: new Date(game.updateDate).toLocaleDateString()
        }));
        this.isLoading = false;
      }),
      catchError(error => {
        console.error('Full error loading games data:', error);
        this.error = error.message || 'Failed to load games data';
        this.isLoading = false;
        return of([]);
      })
    ).subscribe();
  }

  onEdit(gameId: string) {
    this.router.navigate(['/games/edit', gameId]);
  }

  onDelete(gameId: string) {
    if (confirm('Вы уверены, что хотите удалить эту игру?')) {
      this.dataService.deleteGame(gameId).subscribe({
        next: () => {
          this.loadGames();
        },
        error: (error) => {
          console.error('Error deleting game:', error);
        }
      });
    }
  }
}
