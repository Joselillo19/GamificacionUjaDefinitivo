export interface InsigniaInfo {
  id: number;
  actividad: string;
  tipoInsignia: string;
}

export interface AlumnoInsigniasDTO {
  insignias: InsigniaInfo[];
  nivel: string;
  puntos: number;
  posicion: number;
}

export {};
