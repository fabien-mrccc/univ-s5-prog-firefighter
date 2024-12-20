package util;

public record Position(int row, int column) {

    /**
     * Calculates the Euclidean distance between this position and another position.
     *
     * @param other the other position to calculate the distance to
     * @return the Euclidean distance between this position and the other position
     */
    public double distanceTo(Position other) {
        int dx = this.row() - other.row();
        int dy = this.column() - other.column();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
