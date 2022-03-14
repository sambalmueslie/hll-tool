import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServerRoutingModule } from './server-routing.module';
import { ServerBoardComponent } from './server-board/server-board.component';
import { ServerCardComponent } from './server-card/server-card.component';
import { ServerCreateCardComponent } from './server-create-card/server-create-card.component';
import {MaterialModule} from "../material/material.module";
import {FlexModule} from "@angular/flex-layout";
import {ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import { ServerChangeComponent } from './server-change/server-change.component';


@NgModule({
  declarations: [
    ServerBoardComponent,
    ServerCardComponent,
    ServerCreateCardComponent,
    ServerChangeComponent
  ],
  imports: [
    CommonModule,
    ServerRoutingModule,
    MaterialModule,
    FlexModule,
    ReactiveFormsModule,
    TranslateModule
  ]
})
export class ServerModule { }
