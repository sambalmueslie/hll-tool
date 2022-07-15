import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "@auth0/auth0-angular";

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', loadChildren: () => import('./home/home.module').then(m => m.HomeModule)},
  {path: 'community', loadChildren: () => import('./community/community.module').then(m => m.CommunityModule), canActivate: [AuthGuard]},
  {path: 'server', loadChildren: () => import('./server/server.module').then(m => m.ServerModule), canActivate: [AuthGuard]},
  {path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule), canActivate: [AuthGuard]},
  {path: 'game', loadChildren: () => import('./game/game.module').then(m => m.GameModule), canActivate: [AuthGuard]},
  {path: '**', loadChildren: () => import('./error/error.module').then(m => m.ErrorModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
