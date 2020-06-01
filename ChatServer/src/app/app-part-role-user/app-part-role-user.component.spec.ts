import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartRoleUserComponent } from './app-part-role-user.component';

describe('AppPartRoleUserComponent', () => {
  let component: AppPartRoleUserComponent;
  let fixture: ComponentFixture<AppPartRoleUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartRoleUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartRoleUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
