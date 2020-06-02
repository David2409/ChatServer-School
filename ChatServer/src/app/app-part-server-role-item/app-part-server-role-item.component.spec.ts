import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartServerRoleItemComponent } from './app-part-server-role-item.component';

describe('AppPartServerRoleItemComponent', () => {
  let component: AppPartServerRoleItemComponent;
  let fixture: ComponentFixture<AppPartServerRoleItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartServerRoleItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartServerRoleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
