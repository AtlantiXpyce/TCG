package tcg.model.entities;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CardStockDao extends CrudRepository<CardStock, Long>, ListPagingAndSortingRepository<CardStock, Long> {
    
   @Query("""
      SELECT cs FROM CardStock cs
      WHERE cs.card.id = :cardId
      AND (COALESCE(:language, '') = '' OR cs.lang.name = :language)
      ORDER BY cs.price ASC
   """)
   Page<CardStock> findStock(@Param("cardId") Long cardId,
                           @Param("language") String language,
                           Pageable pageable);

}
