package tcg.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tcg.model.services.CardImageService;

@Component
public class ImageLoaderRunner implements CommandLineRunner {

    private final CardImageService cardImageService;

    public ImageLoaderRunner(CardImageService cardImageService) {
        this.cardImageService = cardImageService;
    }

    @Override
    public void run(String... args) throws Exception {
        String imagesFolder = "src/main/resources/static/images/cards";
        cardImageService.loadImagesFromFolder(imagesFolder);
    }
}
