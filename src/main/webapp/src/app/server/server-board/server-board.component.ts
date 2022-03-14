import {Component, OnInit} from '@angular/core';
import {Page} from "../../shared/page/page";
import {ServerService} from "../model/server.service";
import {Server} from "../model/server-api";

@Component({
  selector: 'app-server-board',
  templateUrl: './server-board.component.html',
  styleUrls: ['./server-board.component.scss']
})
export class ServerBoardComponent implements OnInit {

  reloading: boolean = false
  data: Page<Server> | null = null

  constructor(private service: ServerService) {
  }

  ngOnInit(): void {
    this.reload()
  }

  private reload() {
    if (this.reloading) return
    this.reloading = true
    this.service.getServers(0, 50).subscribe(d => this.handleData(d))
  }

  private handleData(d: Page<Server>) {
    this.data = d
    this.reloading = false
  }

}
