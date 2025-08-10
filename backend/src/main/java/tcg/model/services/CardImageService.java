package tcg.model.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.CardDao;

@Service
public class CardImageService {

    private final CardDao cardRepository;

    public CardImageService(CardDao cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public void loadImagesFromFolder(String folderPath) throws IOException {
        Files.list(Path.of(folderPath))
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    String fileName = filePath.getFileName().toString();
                    String cardName = fileName.substring(0, fileName.lastIndexOf('.'));

                    cardRepository.findByName(cardName).ifPresent(card -> {
                        try {
                            byte[] imageBytes = Files.readAllBytes(filePath);
                            card.setImagen(imageBytes);
                            cardRepository.save(card);
                            System.out.println("Imagen guardada para carta: " + cardName);
                        } catch (IOException e) {
                            System.err.println("Error leyendo " + fileName + ": " + e.getMessage());
                        }
                    });
                });
    }
}
