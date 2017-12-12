import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltreDateArriveComponent } from './filtre-date-arrive.component';

describe('FiltreDateArriveComponent', () => {
  let component: FiltreDateArriveComponent;
  let fixture: ComponentFixture<FiltreDateArriveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltreDateArriveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltreDateArriveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
