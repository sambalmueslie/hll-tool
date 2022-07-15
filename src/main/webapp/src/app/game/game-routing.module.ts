import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GameBoardComponent} from './game-board/game-board.component';

const routes: Routes = [
  {path: '', component: GameBoardComponent},
  // {path: 'create', component: ServerChangeComponent},
  // {
  //   path: ':id', component: ServerDetailsViewComponent,
  //   children: [
  //     {path: 'map', component: ServerDetailsMapRotationComponent},
  //     {path: 'player', component: ServerDetailsPlayerListComponent},
  //     {path: 'admin', component: ServerDetailsAdminLogComponent}
  //   ]
  // },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GameRoutingModule {
}
