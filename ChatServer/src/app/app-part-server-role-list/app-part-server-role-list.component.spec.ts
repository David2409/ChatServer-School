import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartServerRoleListComponent } from './app-part-server-role-list.component';

describe('AppPartServerRoleListComponent', () => {
  let component: AppPartServerRoleListComponent;
  let fixture: ComponentFixture<AppPartServerRoleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartServerRoleListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartServerRoleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
