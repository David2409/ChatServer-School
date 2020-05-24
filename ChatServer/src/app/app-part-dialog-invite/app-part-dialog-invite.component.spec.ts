import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartDialogInviteComponent } from './app-part-dialog-invite.component';

describe('AppPartDialogInviteComponent', () => {
  let component: AppPartDialogInviteComponent;
  let fixture: ComponentFixture<AppPartDialogInviteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartDialogInviteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartDialogInviteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
