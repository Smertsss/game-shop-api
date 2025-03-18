import { User } from './user.model';

export interface Role {
  id: string;
  name: string;

  users?: User[];
}

export interface RoleCreateDto {
  name: string;

  userIds?: string[];
}

export interface RoleUpdateDto {
  id: string;
  name?: string;

  userIds?: string[];
}

export interface RolePreview {
  id: string;
  name: string;
}
