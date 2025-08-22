import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FreeGamesComponent } from './free-games.component';

describe('FreeGamesComponent', () => {
  let component: FreeGamesComponent;
  let fixture: ComponentFixture<FreeGamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FreeGamesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FreeGamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
