package tcg.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.CardLanguage;
import tcg.model.entities.CardLanguageDao;

@Service
@Transactional
public class CardLanguageServiceImpl implements CardLanguageService{

    @Autowired
    private CardLanguageDao cardLanguageDao;

    @Override
    public List<CardLanguage> getAllLanguages() {
        return cardLanguageDao.findAll();
    }
    
}
