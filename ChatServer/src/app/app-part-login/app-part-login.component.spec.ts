import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartLoginComponent } from './app-part-login.component';

describe('AppPartLoginComponent', () => {
  let component: AppPartLoginComponent;
  let fixture: ComponentFixture<AppPartLoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartLoginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});