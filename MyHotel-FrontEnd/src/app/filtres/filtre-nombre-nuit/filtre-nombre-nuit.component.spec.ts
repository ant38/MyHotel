import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltreNombreNuitComponent } from './filtre-nombre-nuit.component';

describe('FiltreNombreNuitComponent', () => {
  let component: FiltreNombreNuitComponent;
  let fixture: ComponentFixture<FiltreNombreNuitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltreNombreNuitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltreNombreNuitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
