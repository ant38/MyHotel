import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheBarreComponent } from './recherche-barre.component';

describe('RechercheBarreComponent', () => {
  let component: RechercheBarreComponent;
  let fixture: ComponentFixture<RechercheBarreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RechercheBarreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RechercheBarreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
