package tcg.model.entities;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CardDao extends CrudRepository<Card, Long>, ListPagingAndSortingRepository<Card, Long> {
    Optional<Card> findByName(String name);

    @Query("""
        SELECT c FROM Card c
        WHERE (:name = '' OR LOWER(c.name) LIKE LOWER(:name))
        AND (:setId IS NULL OR c.set.id = :setId)
        AND (:tcgId IS NULL OR c.tcg.id = :tcgId)
    """)
    Page<Card> searchCards(@Param("name") String name,
                        @Param("setId") Long setId,
                        @Param("tcgId") Long tcgId,
                        Pageable pageable);

}
