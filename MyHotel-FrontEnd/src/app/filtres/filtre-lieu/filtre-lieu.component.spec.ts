import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltreLieuComponent } from './filtre-lieu.component';

describe('FiltreLieuComponent', () => {
  let component: FiltreLieuComponent;
  let fixture: ComponentFixture<FiltreLieuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltreLieuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltreLieuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
