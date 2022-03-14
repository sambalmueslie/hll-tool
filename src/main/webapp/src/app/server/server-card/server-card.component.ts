import {Component, Input, OnInit} from '@angular/core';
import {Server} from "../model/server-api";

@Component({
  selector: 'app-server-card',
  templateUrl: './server-card.component.html',
  styleUrls: ['./server-card.component.scss']
})
export class ServerCardComponent implements OnInit {

  @Input()
  data: Server | undefined = undefined

  constructor() {
  }

  ngOnInit(): void {
  }

}
