package io.raindev.stockquotes;

public interface MessageHandler<M> {
    void handle(M message);
}
