public class Hamming {
    private final int distance;

    public Hamming(String leftStrand, String rightStrand) {
        if (leftStrand.length() != rightStrand.length()) {
            if (leftStrand.isEmpty()) {
                throw new IllegalArgumentException("left strand must not be empty.");
            }
            if (rightStrand.isEmpty()) {
                throw new IllegalArgumentException("right strand must not be empty.");
            }
            throw new IllegalArgumentException("leftStrand and rightStrand must be of equal length.");
        }
        this.distance = hammingDistance(leftStrand, rightStrand);
    }

    private int hammingDistance(String leftStrand, String rightStrand) {
        int distance = 0;
        for (int i = 0; i< leftStrand.length(); i++) {
            if (leftStrand.charAt(i) != rightStrand.charAt(i)) {
                ++distance;
            }
        }
        return distance;
    }

    public int getHammingDistance() {
        return distance;
    }
}
