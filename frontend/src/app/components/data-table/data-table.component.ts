import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.css',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
    ]
})
export class DataTableComponent {
  @Input() data: any[] = []; // Данные для таблицы
  @Input() columns: string[] = []; // Названия колонок
}
