import {Component, Inject, Input, OnInit} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {AuthService} from "@auth0/auth0-angular";

@Component({
  selector: 'app-logout-button',
  templateUrl: './logout-button.component.html',
  styleUrls: ['./logout-button.component.scss']
})
export class LogoutButtonComponent implements OnInit {

  @Input()
  collapsed: boolean = false;

  constructor(
    public auth: AuthService,
    @Inject(DOCUMENT) private doc: Document,
  ) {
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.auth.logout({returnTo: this.doc.location.origin});
  }
}
