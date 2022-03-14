import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CommunityRoutingModule} from './community-routing.module';
import {MaterialModule} from "../material/material.module";
import {FlexModule} from "@angular/flex-layout";
import {ReactiveFormsModule} from "@angular/forms";
import {CommunityBoardComponent} from './community-board/community-board.component';
import { CommunityCardComponent } from './community-card/community-card.component';
import { CommunityCreateCardComponent } from './community-create-card/community-create-card.component';
import { CommunityChangeComponent } from './community-change/community-change.component';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    CommunityBoardComponent,
    CommunityCardComponent,
    CommunityCreateCardComponent,
    CommunityChangeComponent
  ],
    imports: [
        CommonModule,
        CommunityRoutingModule,
        MaterialModule,
        FlexModule,
        ReactiveFormsModule,
        TranslateModule
    ]
})
export class CommunityModule {
}
