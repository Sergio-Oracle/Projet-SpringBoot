package sn.ec2lt.library.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ec2lt.library.entity.Livre;
import sn.ec2lt.library.repository.LivreRepository;
import sn.ec2lt.library.service.LivreService;

import java.util.List;

@Service
public class LivreServiceImpl implements LivreService {

    private final LivreRepository livreRepository;

    public LivreServiceImpl(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    @Override
    @Transactional
    public Livre saveLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    @Override
    public Livre getLivre(Long id) {
        return livreRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteLivre(Long id) {
        livreRepository.deleteById(id);
    }

    @Override
    public List<Livre> getLivres() {
        return livreRepository.findAll();
    }
}