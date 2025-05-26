package sn.ec2lt.library.service;

import sn.ec2lt.library.entity.Livre;

import java.util.List;

public interface LivreService {
    Livre getLivre(Long id);
    Livre saveLivre(Livre livre);
    void deleteLivre(Long id);
    List<Livre> getLivres();
}