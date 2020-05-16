import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPartRoomselectionItemComponent } from './app-part-roomselection-item.component';

describe('AppPartRoomselectionItemComponent', () => {
  let component: AppPartRoomselectionItemComponent;
  let fixture: ComponentFixture<AppPartRoomselectionItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPartRoomselectionItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPartRoomselectionItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
