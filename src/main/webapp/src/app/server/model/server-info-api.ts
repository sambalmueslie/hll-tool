import {Server} from "./server-api";

export interface ServerInfo {
  server: Server,
  name: string,
  map: string,
  slots: Slots
}

export interface Slots {
  active: number
  available: number
}
