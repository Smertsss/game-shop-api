import { Company } from './company.model';
import { Role } from './role.model';
import { Game } from './game.model';

export interface User {
  id: string;
  firstName: string;
  secondName: string;
  username: string;
  email: string;
  login: string;
  creationDate: Date;
  lastLoginDate: Date;
  online: boolean;

  roles?: Role[];
  games?: Game[];
  likedGames?: Game[];
  dislikedGames?: Game[];
  companies?: Company[];
}

export interface UserCreateDto {
  firstName: string;
  secondName: string;
  username: string;
  email: string;
  login: string;
  password: string;

  roleIds?: string[];
  gameIds?: string[];
  companyIds?: string[];
}

export interface UserUpdateDto {
  id: string;
  firstName?: string;
  secondName?: string;
  username?: string;
  email?: string;
  login?: string;
  password?: string;
  online?: boolean;

  roleIds?: string[];
  gameIds?: string[];
  companyIds?: string[];
}

export interface AuthUser {
  login: string;
  password: string;
}

export interface AuthResponse {
  user: User;
  token: string;
}

export interface UserPreview {
  id: string;
  username: string;
  email: string;
  online: boolean;

  roleNames?: string[];
}
