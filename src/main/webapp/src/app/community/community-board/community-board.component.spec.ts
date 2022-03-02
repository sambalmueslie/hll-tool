import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunityBoardComponent } from './community-board.component';

describe('CommunityBoardComponent', () => {
  let component: CommunityBoardComponent;
  let fixture: ComponentFixture<CommunityBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommunityBoardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunityBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
