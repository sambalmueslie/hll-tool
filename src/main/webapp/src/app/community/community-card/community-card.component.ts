import {Component, Input, OnInit} from '@angular/core';
import {Community} from "../model/community-api";

@Component({
  selector: 'app-community-card',
  templateUrl: './community-card.component.html',
  styleUrls: ['./community-card.component.scss']
})
export class CommunityCardComponent implements OnInit {

  @Input()
  data: Community | undefined = undefined

  constructor() {
  }

  ngOnInit(): void {
  }

}
