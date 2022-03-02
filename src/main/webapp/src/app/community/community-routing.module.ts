import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommunityBoardComponent} from "./community-board/community-board.component";

const routes: Routes = [
  {path: '', component: CommunityBoardComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommunityRoutingModule {
}
