import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartDialogCreateServerComponent } from './app-part-dialog-create-server.component';

describe('AppPartDialogCreateServerComponent', () => {
  let component: AppPartDialogCreateServerComponent;
  let fixture: ComponentFixture<AppPartDialogCreateServerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartDialogCreateServerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartDialogCreateServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
