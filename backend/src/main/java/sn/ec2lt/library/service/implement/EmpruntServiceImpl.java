package sn.ec2lt.library.service.implement;

import org.springframework.stereotype.Service;
import sn.ec2lt.library.entity.Emprunt;
import sn.ec2lt.library.repository.EmpruntRepository;
import sn.ec2lt.library.service.EmpruntService;

import java.util.List;

@Service
public class EmpruntServiceImpl implements EmpruntService{

    private final EmpruntRepository empruntRepository;

    public EmpruntServiceImpl(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    @Override
    public Emprunt getEmprunt(Long id) {
        return empruntRepository.findById(id).orElse(null);
    }

    @Override
    public Emprunt saveEmprunt(Emprunt emprunt) {
        return empruntRepository.save(emprunt);
    }

    @Override
    public void deleteEmprunt(Long id) {
        empruntRepository.deleteById(id);
    }

    @Override
    public List<Emprunt> getEmpruntsByUser(Long userId) {
        return empruntRepository.findByUserId(userId);
    }
}
