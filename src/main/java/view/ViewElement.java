package view;

import javafx.scene.image.Image;

import java.util.Objects;

public enum ViewElement {

    FIREFIGHTER(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/firefighter.png")))),
    FIRE(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/fire.png")))),
    EMPTY(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/empty.png")))),
    CLOUD(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/cloud.png")))),
    FIRETRUCK(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/firetruck.png")))),
    MOUNTAIN(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/mountain.png")))),
    ROAD(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/road.png")))),
    ROCK(new Image(Objects.requireNonNull(ViewElement.class.getResourceAsStream("/view/rock.png"))));

    private final Image sprite;

    ViewElement(Image sprite) {
        this.sprite = sprite;
    }

    public Image getSprite() {
        return sprite;
    }
}
