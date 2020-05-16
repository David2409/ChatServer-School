import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartMessageComponent } from './app-part-message.component';

describe('AppPartMessageComponent', () => {
  let component: AppPartMessageComponent;
  let fixture: ComponentFixture<AppPartMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
