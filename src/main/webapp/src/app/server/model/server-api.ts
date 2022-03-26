export interface Server {
  id: number,
  name: string
  description: string
}

export class ServerChangeRequest {
  constructor(
    public name: string,
    public description: string,
    public connection: ServerConnectionChangeRequest
  ) {
  }
}

export class ServerConnectionChangeRequest {
  constructor(
    public host: string,
    public port: number,
    public password: string
  ) {
  }
}

