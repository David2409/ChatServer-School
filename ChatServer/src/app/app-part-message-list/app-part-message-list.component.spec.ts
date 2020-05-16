import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartMessageListComponent } from './app-part-message-list.component';

describe('AppPartMessageListComponent', () => {
  let component: AppPartMessageListComponent;
  let fixture: ComponentFixture<AppPartMessageListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartMessageListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartMessageListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
