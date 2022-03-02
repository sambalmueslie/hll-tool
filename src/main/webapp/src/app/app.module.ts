import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {AuthHttpInterceptor, AuthModule} from "@auth0/auth0-angular";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from "@angular/flex-layout";
import {MaterialModule} from "./material/material.module";
import {AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {AuthenticationModule} from "./auth/authentication.module";
import {IdTokenHttpInterceptor} from "./auth/id-token-http.interceptor";

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    AuthModule.forRoot({
      domain: 'hlltool.eu.auth0.com',
      clientId: 'g3jgo0n5INYtc6hsnhbFEaDP9M8lpS5D',
      httpInterceptor: {
        allowedList: [
          '/api/*',
          {
            uri: 'api/*',
          }
        ]
      }
    }),
    AuthenticationModule

  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'de-DE'},
    {provide: HTTP_INTERCEPTORS, useClass: IdTokenHttpInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
