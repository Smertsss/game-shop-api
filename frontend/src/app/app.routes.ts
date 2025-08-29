import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AuthGuard } from './authentication/auth-guard';
import { MainGamesComponent } from './components/main-games/main-games.component';
import { NewGamesComponent } from './components/new-games/new-games.component';
import { UpdateGamesComponent } from './components/update-games/update-games.component';
import { TopGamesComponent } from './components/top-games/top-games.component';
import { UsersDataComponent } from './components/users-data/users-data.component';
import { GamesDataComponent } from './components/games-data/games-data.component';
import { CompaniesDataComponent } from './components/companies-data/companies-data.component';
import { GenresDataComponent } from './components/genres-data/genres-data.component';
import { GameEditComponent } from './components/game-edit/game-edit.component';
import { FreeGamesComponent } from './components/free-games/free-games.component';
import { RegisterComponent } from './components/register/register.component';
import { ErrorNotFoundComponent } from './components/error-not-found/error-not-found.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'main-games', component: MainGamesComponent },
  { path: 'new-game', component: NewGamesComponent },
  { path: 'update-games', component: UpdateGamesComponent },
  { path: 'top-games', component: TopGamesComponent },
  { path: 'free-games', component: FreeGamesComponent },
  { path: 'error-not-found', component: ErrorNotFoundComponent },
  { path: 'users-data', component: UsersDataComponent, canActivate: [AuthGuard] },
  { path: 'games-data', component: GamesDataComponent, canActivate: [AuthGuard] },
  { path: 'companies-data', component: CompaniesDataComponent, canActivate: [AuthGuard] },
  { path: 'games/edit/:id', component: GameEditComponent, canActivate: [AuthGuard] },
  { path: 'genres-data', component: GenresDataComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/main-games', pathMatch: 'full' },
  { path: '**', redirectTo: '/error-not-found' }
  ];
