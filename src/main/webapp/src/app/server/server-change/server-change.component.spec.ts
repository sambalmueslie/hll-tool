import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerChangeComponent } from './server-change.component';

describe('ServerChangeComponent', () => {
  let component: ServerChangeComponent;
  let fixture: ComponentFixture<ServerChangeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServerChangeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerChangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
