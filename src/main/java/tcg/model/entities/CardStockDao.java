package tcg.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CardStockDao extends CrudRepository<CardStock, Long>, ListPagingAndSortingRepository<CardStock, Long> {}
