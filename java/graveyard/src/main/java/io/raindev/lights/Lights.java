package io.raindev.lights;

import java.util.BitSet;

public class Lights {
    /**
     * Returns number of time spans when all lights will be turned out.
     * Light n is is on only when all lights < n are on.
     *
     * @param switches sequence of lights being switched on
     * @return number of times when all switched on lights are on
     */
    public static int lightMoments(int[] switches) {
        BitSet lights = new BitSet();
        int moments = 0;
        int nextOffLight = 0; // next switch in the sequence that needs to be turned on
        for (int i = 0; i < switches.length; i++) {
            // lights numbered starting from 1
            final int switchIndex = switches[i] - 1;
            lights.set(switchIndex);
            if (switchIndex == nextOffLight) {
                nextOffLight = lights.nextClearBit(nextOffLight);
                if (nextOffLight > i) {
                    moments += 1;
                }
            }
        }
        return moments;
    }
}
