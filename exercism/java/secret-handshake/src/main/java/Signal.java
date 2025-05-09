enum Signal {

    WINK, DOUBLE_BLINK, CLOSE_YOUR_EYES, JUMP;

    static boolean containedIn(final int number, final Signal signal) {
        return (number & (1 << signal.ordinal())) != 0;
    }

    static boolean shouldReverse(final int number) {
        return (number & 0b10000) != 0;
    }

}
