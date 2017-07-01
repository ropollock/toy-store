package domain;

import java.util.Random;

public class GeoService {

    /**
     * geoDistance
     * <p>
     * Shim for a geo location endpoint that takes two addresses
     * and returns a float.
     * This shim will just see if the addresses are the same, if so return 0.
     * If not it will just choose a random float to simulate the endpoint.
     *
     * @return float
     */
    public float getDistance(final String addr1, final String addr2) {
        if(addr1.equals(addr2)) {
            return 0.0f;
        }
        Random r = new Random();
        return r.nextFloat() * 1000.0f;
    }
}
