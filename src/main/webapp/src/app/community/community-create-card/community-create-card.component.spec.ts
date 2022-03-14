import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunityCreateCardComponent } from './community-create-card.component';

describe('CommunityCreateCardComponent', () => {
  let component: CommunityCreateCardComponent;
  let fixture: ComponentFixture<CommunityCreateCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommunityCreateCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunityCreateCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
