package carbon.carbon_be.domain.Image.service.resolver;

import org.springframework.stereotype.Component;

@Component
public class CompositeKeyResolver {

    public String resolve(String combosPrefix,
                          String hatType, String hatColor,
                          String clothesType, String clothesColor,
                          String shoesType, String shoesColor) {

        String hat = "hat=" + sanitize(hatType) + "_" + sanitize(hatColor);
        String clothes = "clothes=" + sanitize(clothesType) + "_" + sanitize(clothesColor);
        String shoesFile = "shoes=" + sanitize(shoesType) + "_" + sanitize(shoesColor) + ".png";

        return String.format("%s/%s/%s/%s", combosPrefix, hat, clothes, shoesFile);
    }

    private String sanitize(String raw) {
        return raw.trim().toLowerCase().replaceAll("[^a-z0-9_-]", "");
    }
}


