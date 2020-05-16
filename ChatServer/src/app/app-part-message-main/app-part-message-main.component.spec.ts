import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartMessageMainComponent } from './app-part-message-main.component';

describe('AppPartMessageMainComponent', () => {
  let component: AppPartMessageMainComponent;
  let fixture: ComponentFixture<AppPartMessageMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartMessageMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartMessageMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
