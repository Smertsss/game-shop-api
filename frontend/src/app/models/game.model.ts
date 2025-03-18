import { User } from './user.model';
import { Company } from './company.model';
import { Genre } from './genre.model';

export interface Game {
  id: string;
  name: string;
  context: string;
  cost: number;
  creationDate: Date;
  updateDate: Date;

  users?: User[];
  likedByUsers?: User[];
  dislikedByUsers?: User[];
  companies?: Company[];
  genres?: Genre[];
}

export interface GameCreateDto {
  name: string;
  context: string;
  cost: number;

  users?: User[];
  genreIds?: string[];
  companyIds?: string[];
}

export interface GameUpdateDto {
  id: string;
  name?: string;
  context?: string;
  cost?: number;
  updateDate?: Date;

  users?: User[];
  likedByUsers?: User[];
  dislikedByUsers?: User[];
  genreIds?: string[];
  companyIds?: string[];
}

export interface GamePreview {
  id: string;
  name: string;
  context: string;
  cost: number;
  creationDate: Date;
  updateDate: Date;

  genreNames?: string[];
  companyNames?: string[];
}
