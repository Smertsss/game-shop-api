import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainGamesComponent } from './main-games.component';

describe('MainGamesComponent', () => {
  let component: MainGamesComponent;
  let fixture: ComponentFixture<MainGamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainGamesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainGamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
