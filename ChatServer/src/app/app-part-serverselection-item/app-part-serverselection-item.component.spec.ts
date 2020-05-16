import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartServerselectionItemComponent } from './app-part-serverselection-item.component';

describe('AppPartServerselectionItemComponent', () => {
  let component: AppPartServerselectionItemComponent;
  let fixture: ComponentFixture<AppPartServerselectionItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartServerselectionItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartServerselectionItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
