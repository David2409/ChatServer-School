import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartRoomRoleListComponent } from './app-part-room-role-list.component';

describe('AppPartRoomRoleListComponent', () => {
  let component: AppPartRoomRoleListComponent;
  let fixture: ComponentFixture<AppPartRoomRoleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartRoomRoleListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartRoomRoleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
