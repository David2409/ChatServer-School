import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartServerselectionComponent } from './app-part-serverselection.component';

describe('AppPartServerselectionComponent', () => {
  let component: AppPartServerselectionComponent;
  let fixture: ComponentFixture<AppPartServerselectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartServerselectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartServerselectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
