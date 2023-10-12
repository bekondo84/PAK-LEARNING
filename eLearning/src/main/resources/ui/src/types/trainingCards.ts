export interface AvailableType{
    nom: string,
    nomTheme: string,
    formateur: string,
    lecons: number,
    duree: string
}

export interface SubscribedType{
    nom: string,
    nomTheme: string,
    formateur: string,
    lecons: number,
    membres: number,
    duree: string
}

export interface OngoingType{
    nom: string,
    nomTheme: string,
    formateur: string,
    leconTerminees: number,
    leconsTotal: number,
    progres:number,
    dateDebut: string,
    prochainCours: string,
}

export interface CompleteType{
    nom: string,
    nomTheme: string,
    dateFin: string,
    duree: string,
    leconTerminees: number,
    leconsTotal: number,
    progres:number,
    formateur: string,
}