import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltrePrixComponent } from './filtre-prix.component';

describe('FiltrePrixComponent', () => {
  let component: FiltrePrixComponent;
  let fixture: ComponentFixture<FiltrePrixComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltrePrixComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltrePrixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
