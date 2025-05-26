package sn.ec2lt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ec2lt.library.entity.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {
}