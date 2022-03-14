import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommunityBoardComponent} from "./community-board/community-board.component";
import {CommunityChangeComponent} from './community-change/community-change.component';

const routes: Routes = [
  {path: '', component: CommunityBoardComponent},
  {path: 'create', component: CommunityChangeComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommunityRoutingModule {
}
