package sn.ec2lt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.ec2lt.library.entity.Emprunt;

import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long>{
    @Query("select e from Emprunt e where e.idUtilisateur = :userId")
    List<Emprunt> findByUserId(@Param("userId") Long userId);

}
