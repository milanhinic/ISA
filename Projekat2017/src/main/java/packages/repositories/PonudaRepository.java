package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.Ponuda;

public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

}
