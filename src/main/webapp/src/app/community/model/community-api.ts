export interface Community {
  name: string
  description: string
}

export class CommunityChangeRequest {
  constructor(
    public name: string,
    public description: string,
  ) {
  }
}
