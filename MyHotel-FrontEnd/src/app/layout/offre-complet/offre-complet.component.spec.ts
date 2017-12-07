import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OffreCompletComponent } from './offre-complet.component';

describe('OffreCompletComponent', () => {
  let component: OffreCompletComponent;
  let fixture: ComponentFixture<OffreCompletComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OffreCompletComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OffreCompletComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
