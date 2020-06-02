import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartRoomRoleItemComponent } from './app-part-room-role-item.component';

describe('AppPartRoomRoleItemComponent', () => {
  let component: AppPartRoomRoleItemComponent;
  let fixture: ComponentFixture<AppPartRoomRoleItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartRoomRoleItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartRoomRoleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
