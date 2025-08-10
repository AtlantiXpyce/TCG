package tcg.model.entities;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CardDao extends CrudRepository<Card, Long>, ListPagingAndSortingRepository<Card, Long> {
    Optional<Card> findByName(String name);
}
