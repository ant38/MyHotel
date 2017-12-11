import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OffreDescComponent } from './offre-desc.component';

describe('OffreDescComponent', () => {
  let component: OffreDescComponent;
  let fixture: ComponentFixture<OffreDescComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OffreDescComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OffreDescComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
