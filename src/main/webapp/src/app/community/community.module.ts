import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CommunityRoutingModule} from './community-routing.module';
import {MaterialModule} from "../material/material.module";
import {FlexModule} from "@angular/flex-layout";
import {ReactiveFormsModule} from "@angular/forms";
import {CommunityBoardComponent} from './community-board/community-board.component';


@NgModule({
  declarations: [
    CommunityBoardComponent
  ],
  imports: [
    CommonModule,
    CommunityRoutingModule,
    MaterialModule,
    FlexModule,
    ReactiveFormsModule
  ]
})
export class CommunityModule {
}
