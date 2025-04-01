import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  //{ path: '', redirectTo: '/home', pathMatch: 'full' },
  //{ path: '**', component: NavbarComponent }
  ];
