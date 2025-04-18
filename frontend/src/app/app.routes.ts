import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AuthGuard } from './authentication/auth-guard';
import { MainGamesComponent } from './components/main-games/main-games.component';
import { NewGamesComponent } from './components/new-games/new-games.component';
import { UpdateGamesComponent } from './components/update-games/update-games.component';
import { TopGamesComponent } from './components/top-games/top-games.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  //{ path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'main-games', component: MainGamesComponent },
  { path: 'new-game', component: NewGamesComponent },
  { path: 'update-games', component: UpdateGamesComponent },
  { path: 'top-games', component: TopGamesComponent },
  { path: '', redirectTo: '/main-games', pathMatch: 'full' },
  { path: '**', redirectTo: '/main-games' }
  ];
