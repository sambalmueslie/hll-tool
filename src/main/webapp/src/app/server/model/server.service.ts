import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/base-service";
import {HttpClient} from "@angular/common/http";
import {LoggingService} from "../../shared/logging/logging.service";
import {Observable} from "rxjs";
import {Page} from "../../shared/page/page";
import {Server, ServerChangeRequest} from './server-api';

@Injectable({
  providedIn: 'root'
})
export class ServerService extends BaseService {

  constructor(http: HttpClient, logger: LoggingService) {
    super(http, 'logic/server', logger)
  }

  getServers(page: number = 0, size: number = 50): Observable<Page<Server>> {
    return this.getPaged('', page, size)
  }

  getServer(id: number): Observable<Server> {
    return this.get('' + id)
  }

  createServer(request: ServerChangeRequest): Observable<Server> {
    return this.post('', request)
  }

  updateServer(id: number, request: ServerChangeRequest): Observable<Server> {
    return this.put('' + id, request)
  }

  deleteServer(id: number): Observable<any> {
    return this.delete('' + id)
  }
}
