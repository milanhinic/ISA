package packages.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import packages.beans.Korisnik;

public interface KorisnikRepository extends PagingAndSortingRepository<Korisnik,Long>{

}
