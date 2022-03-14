import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Location} from "@angular/common";
import {ServerService} from "../model/server.service";
import {Server} from "../model/server-api";

@Component({
  selector: 'app-server-change',
  templateUrl: './server-change.component.html',
  styleUrls: ['./server-change.component.scss']
})
export class ServerChangeComponent implements OnInit {


  reloading: boolean = false

  serverForm: FormGroup

  constructor(fb: FormBuilder, private service: ServerService, private _snackBar: MatSnackBar, private location: Location) {
    this.serverForm = fb.group({
      name: fb.control('', Validators.required),
      description: fb.control(''),
      connection: fb.group({
        host: fb.control('', Validators.required),
        port: fb.control('', Validators.required),
        password: fb.control('', Validators.required),
      }),
    })
  }

  ngOnInit(): void {
  }

  getHeaderText(): string {
    return "Create new server"
  }

  submit() {
    if (!this.serverForm.valid) {
      return;
    }
    let request = this.serverForm.value
    this.reloading = true
    this.service.createServer(request).subscribe(d => this.handleCreated(d))
  }

  private handleCreated(d: Server) {
    this.reloading = false
    this._snackBar.open("Server created", "OK", {
      duration: 5000,
    }).onAction().subscribe(_ => this.location.back());
  }

}
