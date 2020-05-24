import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartDialogCreateRoomComponent } from './app-part-dialog-create-room.component';

describe('AppPartDialogCreateRoomComponent', () => {
  let component: AppPartDialogCreateRoomComponent;
  let fixture: ComponentFixture<AppPartDialogCreateRoomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartDialogCreateRoomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartDialogCreateRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
