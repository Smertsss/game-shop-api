import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users-data',
  templateUrl: './users-data.component.html',
  styleUrl: './users-data.component.css',
  standalone: true,
  imports: [
    FormsModule,
    DataTableComponent,
    CommonModule
    ]
})
export class UsersDataComponent {
  usersData: any[] = [];
  columns: string[] = ['firstName', 'secondName', 'username', 'email', 'creationDate', 'lastLoginDate', 'online',
    'games', 'likedGames', 'dislikedGames', 'roles', 'companies', 'images'];
  isLoading = true;
  error: string | null = null;

  constructor(
    private dataService: DataTableService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.dataService.getUsersData().pipe(
      tap(data => {
        this.usersData = data.map(user => ({
          ...user,
          creationDate: new Date(user.creationDate).toLocaleDateString(),
          lastLoginDate: new Date(user.lastLoginDate).toLocaleDateString(),
          games: user.games?.toString() || '0',
          likedGames: user.likedGames?.toString() || '0',
          dislikedGames: user.dislikedGames?.toString() || '0',
          roles: user.roles?.toString() || '0',
          companies: user.companies?.toString() || '0',
          images: user.images?.toString() || '0'
        }));
        this.isLoading = false;
      }),
      catchError(error => {
        console.error('Full error loading users data:', error);
        this.error = error.message || 'Failed to load users data';
        this.isLoading = false;
        return of([]);
      })
    ).subscribe();
  }

  onEdit(userID: string) {
    this.router.navigate(['/clients/edit', userID]);
  }

  onDelete(userID: string) {
    if (confirm('Вы уверены, что хотите удалить этого пользователя?')) {
      this.dataService.deleteUser(userID).subscribe({
        next: () => {
          this.loadUsers();
        },
        error: (error) => {
          console.error('Error deleting user:', error);
        }
      });
    }
  }
}
