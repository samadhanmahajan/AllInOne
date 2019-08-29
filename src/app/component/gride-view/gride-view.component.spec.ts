import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrideViewComponent } from './gride-view.component';

describe('GrideViewComponent', () => {
  let component: GrideViewComponent;
  let fixture: ComponentFixture<GrideViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrideViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrideViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
