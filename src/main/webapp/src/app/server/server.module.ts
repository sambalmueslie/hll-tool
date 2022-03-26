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
import { ServerDetailsViewComponent } from './server-details-view/server-details-view.component';
import { ServerDetailsMapRotationComponent } from './server-details-map-rotation/server-details-map-rotation.component';
import { ServerDetailsPlayerListComponent } from './server-details-player-list/server-details-player-list.component';
import { ServerDetailsAdminLogComponent } from './server-details-admin-log/server-details-admin-log.component';


@NgModule({
  declarations: [
    ServerBoardComponent,
    ServerCardComponent,
    ServerCreateCardComponent,
    ServerChangeComponent,
    ServerDetailsViewComponent,
    ServerDetailsMapRotationComponent,
    ServerDetailsPlayerListComponent,
    ServerDetailsAdminLogComponent
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
