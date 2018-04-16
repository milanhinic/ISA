package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Karta;

public interface KartaRepository extends JpaRepository<Karta, Long>{

}
