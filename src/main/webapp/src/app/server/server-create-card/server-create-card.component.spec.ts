import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerCreateCardComponent } from './server-create-card.component';

describe('ServerCreateCardComponent', () => {
  let component: ServerCreateCardComponent;
  let fixture: ComponentFixture<ServerCreateCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServerCreateCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerCreateCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
