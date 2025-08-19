package tcg.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.Tcg;
import tcg.model.entities.TcgDao;

@Service
@Transactional
public class TcgServiceImpl implements TcgService{

    @Autowired
    private TcgDao tcgDao;

    @Override
    public List<Tcg> getAllTcgs() {
        return tcgDao.findAll();
    }
}
