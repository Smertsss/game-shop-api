import { Game } from './game.model';

export interface Genre {
  id: string;
  name: string;

  games?: Game[];
}

export interface GenreCreateDto {
  name: string;

  gameIds?: string[];
}

export interface GenreUpdateDto {
  id: string;
  name?: string;

  gameIds?: string[];
}

export interface GenreData {
  id: string;
  name: string;
}

