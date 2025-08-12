package tcg.model.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TcgDao extends CrudRepository<Tcg, Long>{
    @Override
    List<Tcg> findAll();
}
