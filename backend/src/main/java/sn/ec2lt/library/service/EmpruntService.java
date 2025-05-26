package sn.ec2lt.library.service;

import sn.ec2lt.library.entity.Emprunt;

import java.util.List;

public interface EmpruntService {
    Emprunt getEmprunt(Long id);
    Emprunt saveEmprunt(Emprunt emprunt);
    void deleteEmprunt(Long id);
    List<Emprunt> getEmpruntsByUser(Long userId);
}
