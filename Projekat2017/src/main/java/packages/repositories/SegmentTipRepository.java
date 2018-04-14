package packages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import packages.beans.TipSegmenta;

public interface SegmentTipRepository extends JpaRepository<TipSegmenta,Long>{

}
