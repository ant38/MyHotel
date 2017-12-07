import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecherchePlusComponent } from './recherche-plus.component';

describe('RecherchePlusComponent', () => {
  let component: RecherchePlusComponent;
  let fixture: ComponentFixture<RecherchePlusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecherchePlusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecherchePlusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
