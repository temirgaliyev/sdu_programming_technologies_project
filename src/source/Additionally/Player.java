package source.Additionally;

public interface Player {
    /**
     * Move {@link Player} To The RIGHT
     */
    void moveRight();

    /**
     * Move {@link Player} To The LEFT
     */
    void moveLeft();

    /**
     * Move {@link Player} To The UP
     */
    void moveUp();

    /**
     * Move {@link Player} To The DOWN
     */
    void moveDown();

    /**
     * @return {@link Position} of the {@link Player}
     */
    Position getPosition();
}
