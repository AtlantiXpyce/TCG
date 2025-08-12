package tcg.model.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardSetDao extends CrudRepository<CardSet, Long>{
    @Override
    List<CardSet> findAll();

}
