import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltreAvisComponent } from './filtre-avis.component';

describe('FiltreAvisComponent', () => {
  let component: FiltreAvisComponent;
  let fixture: ComponentFixture<FiltreAvisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltreAvisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltreAvisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
