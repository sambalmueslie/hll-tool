import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerDetailsMapRotationComponent } from './server-details-map-rotation.component';

describe('ServerDetailsMapRotationComponent', () => {
  let component: ServerDetailsMapRotationComponent;
  let fixture: ComponentFixture<ServerDetailsMapRotationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServerDetailsMapRotationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerDetailsMapRotationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
