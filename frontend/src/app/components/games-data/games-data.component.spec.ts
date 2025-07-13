import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GamesDataComponent } from './games-data.component';

describe('GamesDataComponent', () => {
  let component: GamesDataComponent;
  let fixture: ComponentFixture<GamesDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GamesDataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GamesDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
