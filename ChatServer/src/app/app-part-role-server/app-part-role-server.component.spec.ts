import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartRoleServerComponent } from './app-part-role-server.component';

describe('AppPartRoleServerComponent', () => {
  let component: AppPartRoleServerComponent;
  let fixture: ComponentFixture<AppPartRoleServerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartRoleServerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartRoleServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
