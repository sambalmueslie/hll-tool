import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerDetailsAdminLogComponent } from './server-details-admin-log.component';

describe('ServerDetailsAdminLogComponent', () => {
  let component: ServerDetailsAdminLogComponent;
  let fixture: ComponentFixture<ServerDetailsAdminLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServerDetailsAdminLogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerDetailsAdminLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
