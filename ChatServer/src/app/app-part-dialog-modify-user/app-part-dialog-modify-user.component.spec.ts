import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartDialogModifyUserComponent } from './app-part-dialog-modify-user.component';

describe('AppPartDialogModifyUserComponent', () => {
  let component: AppPartDialogModifyUserComponent;
  let fixture: ComponentFixture<AppPartDialogModifyUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartDialogModifyUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartDialogModifyUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
