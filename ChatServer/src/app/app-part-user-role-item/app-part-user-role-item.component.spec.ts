import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartUserRoleItemComponent } from './app-part-user-role-item.component';

describe('AppPartUserRoleItemComponent', () => {
  let component: AppPartUserRoleItemComponent;
  let fixture: ComponentFixture<AppPartUserRoleItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartUserRoleItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartUserRoleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
