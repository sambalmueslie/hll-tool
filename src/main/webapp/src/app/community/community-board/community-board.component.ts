import {Component, OnInit} from '@angular/core';
import {CommunityService} from "../model/community.service";
import {Community} from "../model/community-api";
import {Page} from "../../shared/page/page";

@Component({
  selector: 'app-community-board',
  templateUrl: './community-board.component.html',
  styleUrls: ['./community-board.component.scss']
})
export class CommunityBoardComponent implements OnInit {

  reloading: boolean = false
  data: Page<Community> | null = null

  constructor(private service: CommunityService) {
  }

  ngOnInit(): void {
    this.reload()
  }

  private reload() {
    if (this.reloading) return
    this.reloading = true
    this.service.getCommunities(0, 50).subscribe(d => this.handleData(d))
  }

  private handleData(d: Page<Community>) {
    this.data = d
    this.reloading = false
  }
}
