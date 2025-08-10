import { TestBed } from '@angular/core/testing';

import { ListOfGameService } from './list-of-game.service';

describe('ListOfGameService', () => {
  let service: ListOfGameService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListOfGameService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
