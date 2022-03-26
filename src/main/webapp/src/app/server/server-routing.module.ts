import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ServerBoardComponent} from './server-board/server-board.component';
import {ServerChangeComponent} from "./server-change/server-change.component";
import {ServerDetailsViewComponent} from "./server-details-view/server-details-view.component";
import {ServerDetailsMapRotationComponent} from "./server-details-map-rotation/server-details-map-rotation.component";
import {ServerDetailsPlayerListComponent} from './server-details-player-list/server-details-player-list.component';
import {ServerDetailsAdminLogComponent} from "./server-details-admin-log/server-details-admin-log.component";

const routes: Routes = [
  {path: '', component: ServerBoardComponent},
  {path: 'create', component: ServerChangeComponent},
  {
    path: ':id', component: ServerDetailsViewComponent,
    children: [
      {path: 'map', component: ServerDetailsMapRotationComponent},
      {path: 'player', component: ServerDetailsPlayerListComponent},
      {path: 'admin', component: ServerDetailsAdminLogComponent}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServerRoutingModule {
}
