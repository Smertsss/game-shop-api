import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ListOfGamesComponent } from '../list-of-games/list-of-games.component';

@Component({
  selector: 'app-new-games',
  templateUrl: './new-games.component.html',
  styleUrl: './new-games.component.css',
  standalone: true,
  imports: [
   FormsModule,
   ListOfGamesComponent
   ]
})
export class NewGamesComponent {

}
