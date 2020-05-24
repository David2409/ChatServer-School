import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartDialogModifyServerComponent } from './app-part-dialog-modify-server.component';

describe('AppPartDialogModifyServerComponent', () => {
  let component: AppPartDialogModifyServerComponent;
  let fixture: ComponentFixture<AppPartDialogModifyServerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartDialogModifyServerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartDialogModifyServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
