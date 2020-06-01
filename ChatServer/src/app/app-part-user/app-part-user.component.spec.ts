import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartUserComponent } from './app-part-user.component';

describe('AppPartUserComponent', () => {
  let component: AppPartUserComponent;
  let fixture: ComponentFixture<AppPartUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
