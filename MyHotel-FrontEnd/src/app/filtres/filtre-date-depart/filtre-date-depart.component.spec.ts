import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltreDateDepartComponent } from './filtre-date-depart.component';

describe('FiltreDateDepartComponent', () => {
  let component: FiltreDateDepartComponent;
  let fixture: ComponentFixture<FiltreDateDepartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltreDateDepartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltreDateDepartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
