import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

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
  columns: string[] = ['id', 'firstName', 'secondName', 'username', 'email', 'login', 'creationDate', 'lastLoginDate', 'online'];
  isLoading = true;
  error: string | null = null;

  constructor(private dataService: DataTableService) {}

  ngOnInit() {
      console.log('Starting to load games data from:', `${this.dataService.apiUrl}/games`);

      this.dataService.getUsers().pipe(
          tap(data => {
            this.usersData = data.map(user => ({
              ...user,
              creationDate: new Date(user.creationDate).toLocaleDateString(),
              updateDate: new Date(user.updateDate).toLocaleDateString()
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
}
