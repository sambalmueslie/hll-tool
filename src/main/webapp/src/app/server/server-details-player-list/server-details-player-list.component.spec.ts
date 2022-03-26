import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerDetailsPlayerListComponent } from './server-details-player-list.component';

describe('ServerDetailsPlayerListComponent', () => {
  let component: ServerDetailsPlayerListComponent;
  let fixture: ComponentFixture<ServerDetailsPlayerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServerDetailsPlayerListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerDetailsPlayerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
