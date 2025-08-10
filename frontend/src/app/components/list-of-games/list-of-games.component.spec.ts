import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOfGamesComponent } from './list-of-games.component';

describe('ListOfGamesComponent', () => {
  let component: ListOfGamesComponent;
  let fixture: ComponentFixture<ListOfGamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListOfGamesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListOfGamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
