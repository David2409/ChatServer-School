import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartUserRoleListComponent } from './app-part-user-role-list.component';

describe('AppPartUserRoleListComponent', () => {
  let component: AppPartUserRoleListComponent;
  let fixture: ComponentFixture<AppPartUserRoleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartUserRoleListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartUserRoleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
