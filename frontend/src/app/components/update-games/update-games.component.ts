import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ListOfGamesComponent } from '../list-of-games/list-of-games.component';

@Component({
  selector: 'app-update-games',
  templateUrl: './update-games.component.html',
  styleUrl: './update-games.component.css',
  standalone: true,
  imports: [
   FormsModule,
   ListOfGamesComponent
   ]
})
export class UpdateGamesComponent {

}
