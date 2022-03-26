import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerDetailsViewComponent } from './server-details-view.component';

describe('ServerDetailsViewComponent', () => {
  let component: ServerDetailsViewComponent;
  let fixture: ComponentFixture<ServerDetailsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServerDetailsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerDetailsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
