import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/base-service";
import {HttpClient} from "@angular/common/http";
import {LoggingService} from "../../shared/logging/logging.service";
import {ServerInfo} from './server-info-api';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServerInfoService extends BaseService {

  constructor(http: HttpClient, logger: LoggingService) {
    super(http, 'logic/server/info', logger)
  }

  getInfo(serverId: number): Observable<ServerInfo> {
    return this.get('' + serverId)
  }

  getMapsInRotation(serverId: number): Observable<Array<string>> {
    return this.get('' + serverId + '/maps')
  }
}
