import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginButtonComponent } from './login-button/login-button.component';
import { SignupButtonComponent } from './signup-button/signup-button.component';
import { LogoutButtonComponent } from './logout-button/logout-button.component';
import { AuthenticationButtonComponent } from './authentication-button/authentication-button.component';
import {FlexModule} from "@angular/flex-layout";
import {MatIconModule} from "@angular/material/icon";
import {TranslateModule} from "@ngx-translate/core";
import {MatListModule} from "@angular/material/list";



@NgModule({
  declarations: [
    LoginButtonComponent,
    SignupButtonComponent,
    LogoutButtonComponent,
    AuthenticationButtonComponent
  ],
  exports: [
    AuthenticationButtonComponent
  ],
  imports: [
    CommonModule,
    FlexModule,
    MatIconModule,
    TranslateModule,
    MatListModule,
  ]
})
export class AuthenticationModule { }
