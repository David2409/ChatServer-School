import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartRoomselectionComponent } from './app-part-roomselection.component';

describe('AppPartRoomselectionComponent', () => {
  let component: AppPartRoomselectionComponent;
  let fixture: ComponentFixture<AppPartRoomselectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartRoomselectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartRoomselectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
