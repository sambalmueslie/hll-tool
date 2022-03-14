import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ServerBoardComponent} from './server-board/server-board.component';
import {ServerChangeComponent} from "./server-change/server-change.component";

const routes: Routes = [
  {path: '', component: ServerBoardComponent},
  {path: 'create', component: ServerChangeComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServerRoutingModule {
}
