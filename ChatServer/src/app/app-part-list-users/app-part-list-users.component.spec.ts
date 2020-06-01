import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartListUsersComponent } from './app-part-list-users.component';

describe('AppPartListUsersComponent', () => {
  let component: AppPartListUsersComponent;
  let fixture: ComponentFixture<AppPartListUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartListUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartListUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
