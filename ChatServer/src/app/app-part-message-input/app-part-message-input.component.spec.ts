import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartMessageInputComponent } from './app-part-message-input.component';

describe('AppPartMessageInputComponent', () => {
  let component: AppPartMessageInputComponent;
  let fixture: ComponentFixture<AppPartMessageInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartMessageInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartMessageInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
