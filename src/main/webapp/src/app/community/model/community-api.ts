export interface Community {
  name: string
}

export class CommunityChangeRequest {
  constructor(
    public name: string
  ) {
  }
}
