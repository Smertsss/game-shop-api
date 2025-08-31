import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-genres-data',
  templateUrl: './genres-data.component.html',
  styleUrl: './genres-data.component.css',
  standalone: true,
  imports: [
    FormsModule,
    DataTableComponent,
    CommonModule
    ]
})
export class GenresDataComponent {
    genresData: any[] = [];
    columns: string[] = ['name', 'games'];
    isLoading = true;
    error: string | null = null;

    constructor(
      private dataService: DataTableService,
      private router: Router
    ) {}

    ngOnInit() {
      this.loadGenres();
    }

    loadGenres() {
      this.dataService.getGenresData().pipe(
        tap(data => {
          this.genresData = data.map(genre => ({
            ...genre,
            games: genre.games?.toString() || '0'
          }));
          this.isLoading = false;
        }),
        catchError(error => {
          console.error('Full error loading genres data:', error);
          this.error = error.message || 'Failed to load genres data';
          this.isLoading = false;
          return of([]);
        })
      ).subscribe();
    }

    onEdit(genreID: string) {
      this.router.navigate(['/genres/edit', genreID]);
    }

    onDelete(genreID: string) {
      if (confirm('Вы уверены, что хотите удалить это жанр?')) {
        this.dataService.deleteUser(genreID).subscribe({
          next: () => {
            this.loadGenres();
          },
          error: (error) => {
            console.error('Error deleting genre:', error);
          }
        });
      }
    }
}
