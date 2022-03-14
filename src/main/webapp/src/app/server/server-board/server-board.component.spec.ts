import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerBoardComponent } from './server-board.component';

describe('ServerBoardComponent', () => {
  let component: ServerBoardComponent;
  let fixture: ComponentFixture<ServerBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServerBoardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
