package tcg.model.entities;

import java.util.List;

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
        AND cs.quantity > 0
        AND (COALESCE(:language, '') = '' OR cs.lang.name = :language)
        ORDER BY cs.price ASC
    """)
    Page<CardStock> findStock(@Param("cardId") Long cardId,
                            @Param("language") String language,
                            Pageable pageable);

    @Query("""
        SELECT cs FROM CardStock cs
        WHERE cs.seller.id = :userId
        ORDER BY cs.id DESC
    """)
    Page<CardStock> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
