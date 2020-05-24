import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartDialogModifyRoomComponent } from './app-part-dialog-modify-room.component';

describe('AppPartDialogModifyRoomComponent', () => {
  let component: AppPartDialogModifyRoomComponent;
  let fixture: ComponentFixture<AppPartDialogModifyRoomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartDialogModifyRoomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartDialogModifyRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
