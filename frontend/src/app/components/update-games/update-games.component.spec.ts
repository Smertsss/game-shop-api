import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateGamesComponent } from './update-games.component';

describe('UpdateGamesComponent', () => {
  let component: UpdateGamesComponent;
  let fixture: ComponentFixture<UpdateGamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateGamesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateGamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
