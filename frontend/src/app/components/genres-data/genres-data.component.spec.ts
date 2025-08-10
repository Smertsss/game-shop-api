import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenresDataComponent } from './genres-data.component';

describe('GenresDataComponent', () => {
  let component: GenresDataComponent;
  let fixture: ComponentFixture<GenresDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenresDataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenresDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
