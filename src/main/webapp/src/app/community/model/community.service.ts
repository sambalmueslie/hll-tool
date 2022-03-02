import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/base-service";
import {HttpClient} from "@angular/common/http";
import {LoggingService} from "../../shared/logging/logging.service";
import {Observable} from "rxjs";
import {Page} from "../../shared/page/page";
import {Community, CommunityChangeRequest} from "./community-api";

@Injectable({
  providedIn: 'root'
})
export class CommunityService extends BaseService {

  constructor(http: HttpClient, logger: LoggingService) {
    super(http, 'api/logic/community', logger)
  }

  getCommunities(page: number = 0, size: number = 50): Observable<Page<Community>> {
    return this.getPaged('', page, size)
  }

  getCommunity(id: number): Observable<Community> {
    return this.get('' + id)
  }

  createCommunity(request: CommunityChangeRequest): Observable<Community> {
    return this.post('', request)
  }

  updateCommunity(id: number, request: CommunityChangeRequest): Observable<Community> {
    return this.put('' + id, request)
  }

  deleteCommunity(id: number): Observable<any> {
    return this.delete('' + id)
  }
}
