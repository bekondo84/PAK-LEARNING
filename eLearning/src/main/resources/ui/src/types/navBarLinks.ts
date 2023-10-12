export interface BackEndLinkType{
    badge: string,
    code: string,
    icon: string | null,
    sequence: number,
    text: string,
    total:number
}

export interface NavLinkType{
    name: string,
    href: {name: string},
    total: number
}