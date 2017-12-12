import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltrePetitDejeunerComponent } from './filtre-petit-dejeuner.component';

describe('FiltrePetitDejeunerComponent', () => {
  let component: FiltrePetitDejeunerComponent;
  let fixture: ComponentFixture<FiltrePetitDejeunerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltrePetitDejeunerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltrePetitDejeunerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
