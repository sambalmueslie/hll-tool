import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunityChangeComponent } from './community-change.component';

describe('CommunityChangeComponent', () => {
  let component: CommunityChangeComponent;
  let fixture: ComponentFixture<CommunityChangeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommunityChangeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunityChangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
