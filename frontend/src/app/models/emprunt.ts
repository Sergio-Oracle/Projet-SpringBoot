export interface Emprunt {
  id: number;
  idUtilisateur: number;
  idLivre: number;
  dateEmprunt: string;
  dateRetour: string | null;
}
