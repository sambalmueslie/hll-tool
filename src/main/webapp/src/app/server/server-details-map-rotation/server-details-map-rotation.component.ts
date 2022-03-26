import {Component, OnInit} from '@angular/core';
import {ServerInfoService} from "../model/server-info.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-server-details-map-rotation',
  templateUrl: './server-details-map-rotation.component.html',
  styleUrls: ['./server-details-map-rotation.component.scss']
})
export class ServerDetailsMapRotationComponent implements OnInit {

  reloading: boolean = false
  serverId: number = -1
  maps: string[] = []

  constructor(private service: ServerInfoService, private router: Router, private activatedRoute: ActivatedRoute) {
    // @ts-ignore
    this.serverId = Number(this.activatedRoute.parent.snapshot.paramMap.get('id'))
  }

  ngOnInit(): void {
    this.reload()
  }

  private reload() {
    if (this.reloading) return
    this.reloading = true
    this.service.getMapsInRotation(this.serverId).subscribe(d => this.handleData(d))
  }

  private handleData(d: Array<string>) {
    this.maps = d
    this.reloading = false
  }
}
