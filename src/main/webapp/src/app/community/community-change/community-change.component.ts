import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommunityService} from "../model/community.service";
import {Community} from "../model/community-api";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Location} from '@angular/common'

@Component({
  selector: 'app-community-change',
  templateUrl: './community-change.component.html',
  styleUrls: ['./community-change.component.scss']
})
export class CommunityChangeComponent implements OnInit {

  reloading: boolean = false

  communityForm: FormGroup

  constructor(fb: FormBuilder, private service: CommunityService, private _snackBar: MatSnackBar, private location: Location) {
    this.communityForm = fb.group({
      name: fb.control('', Validators.required),
      description: fb.control('')
    })
  }

  ngOnInit(): void {
  }

  getHeaderText(): string {
    return "Create new community"
  }

  submit() {
    if (!this.communityForm.valid) {
      return;
    }
    let request = this.communityForm.value
    this.reloading = true
    this.service.createCommunity(request).subscribe(d => this.handleCreated(d))
  }

  private handleCreated(d: Community) {
    this.reloading = false
    this._snackBar.open("Community created", "OK", {
      duration: 5000,
    }).onAction().subscribe(_ => this.location.back());
  }
}
