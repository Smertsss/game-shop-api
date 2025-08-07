import { User } from './user.model';
import { Game } from './game.model';

export interface Company {
  id: string;
  name: string;
  context: string;
  creationDate: Date;

  users?: User[];
  games?: Game[];
}

export interface CompanyCreateDto {
  name: string;
  context: string;

  users?: User[];
  games?: Game[];
}

export interface CompanyUpdateDto {
  id: string;
  name?: string;
  context?: string;

  users?: User[];
  games?: Game[];
}

export interface CompanyData {
  id: string;
  name: string;
  context: string;
  creationDate: string;
}
