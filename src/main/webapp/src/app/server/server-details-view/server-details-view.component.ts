import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ServerInfo} from "../model/server-info-api";
import {ServerInfoService} from "../model/server-info.service";
import {Subscription, timer} from "rxjs";

@Component({
  selector: 'app-server-details-view',
  templateUrl: './server-details-view.component.html',
  styleUrls: ['./server-details-view.component.scss']
})
export class ServerDetailsViewComponent implements OnInit {

  reloading: boolean = false
  info: ServerInfo | null = null
  serverId: number = -1
  activeLink: string = ""
  private readonly interval: Subscription;

  constructor(private service: ServerInfoService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.serverId = Number(this.activatedRoute.snapshot.paramMap.get('id'))

    this.interval = timer(0, 5000).subscribe(_ => this.refresh())

  }

  ngOnInit(): void {
    this.reload()
  }

  ngOnDestroy(): void {
    if (this.interval) {
      this.interval.unsubscribe()
    }
  }

  private reload() {
    if (this.reloading) return
    this.reloading = true
    this.service.getInfo(this.serverId).subscribe(d => this.handleData(d))
  }

  private handleData(d: ServerInfo) {
    this.info = d
    this.reloading = false
  }

  private refresh() {
    if (this.reloading) return
    this.service.getInfo(this.serverId).subscribe(d => this.handleData(d))
  }
}
