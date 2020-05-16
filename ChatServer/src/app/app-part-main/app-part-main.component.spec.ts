import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartMainComponent } from './app-part-main.component';

describe('AppPartMaint', () => {
  let component: AppPartMainComponent;
  let fixture: ComponentFixture<AppPartMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
