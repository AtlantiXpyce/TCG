package tcg.model.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardLanguageDao extends CrudRepository<CardLanguage, Long>{
    @Override
    List<CardLanguage> findAll();
}
