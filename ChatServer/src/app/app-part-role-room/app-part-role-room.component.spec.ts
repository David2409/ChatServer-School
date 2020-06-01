import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartRoleRoomComponent } from './app-part-role-room.component';

describe('AppPartRoleRoomComponent', () => {
  let component: AppPartRoleRoomComponent;
  let fixture: ComponentFixture<AppPartRoleRoomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartRoleRoomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartRoleRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
