import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltreNombrePersonneComponent } from './filtre-nombre-personne.component';

describe('FiltreNombrePersonneComponent', () => {
  let component: FiltreNombrePersonneComponent;
  let fixture: ComponentFixture<FiltreNombrePersonneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltreNombrePersonneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltreNombrePersonneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
