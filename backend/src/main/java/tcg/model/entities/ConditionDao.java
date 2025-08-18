package tcg.model.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ConditionDao extends CrudRepository<Condition, Long>{
    @Override
    List<Condition> findAll();
}
