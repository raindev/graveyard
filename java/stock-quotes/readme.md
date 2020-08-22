# Stock Quotes

## Order of event processing

The functionality provided by the service is backed by an agregated view of the data from two incoming streams. The streams are delivered via independent channels (separate web-socket connections), so there's no ordering relationship between messages of the two streams. Given consistently assigned timestamps for stream messages, it would be possible to process messages of both streams in order. This would necessitate a delay in processing, to introduce a window of time within which messages of one stream might be delayed in relation to the other stream. Because the partners do not provide message timestamps, the streams will have to be processed in real-time order, as the data is received.

### Out of order processing anomalies

The messages of the two stream might be processed out of order, relatively to the logical sequence of events, which might lead to certain anomalies, or unexpected behaviours of the service.

With regards to Add messages, reordering them with Quote messages will lead to receiving quotes for an instrument which doesn't exist. In this case, the quotes can either be simply discarded or retained until an instrument infromation is received later. This might lead to high volatility events being missed for recently created instrument.

Similarly, quotes can be received after Remove message for the same instrument. Discarding price updates for a shortly removed instrument is unlikely to be a problem.

If a Remove message is followed by an Add message, this might lead to quotes being assigned to the wrong instrument. Delayed quotes for the old instrument might be incorrectly assigned to the new instrument and vise versa. This might also lead to high volatility events being incorrectly triggered if the price of the old and the new instruments differ significantly. One way to mitigate the issue for new instruments is to introduce a cool down after creation during which no events are emitted. The best solution however would be for the partner stream to avoid reusing ISINs withing a short period of time.

For the simplicities sake, the implementation will discard quotes for non-existent instruments and will assume that reuse of ISIN withing a short time frame is unlikely.

## Price history

The removal of old candles, as well as the creation of new ones, is triggered by incoming Quote messages. This means that the old candles will be kept around if no new quotes are received for a period of time longer than the duration of retained history. If this impacts memory usage significantly, removal of no longer needed data can be done with a background cleanup thread.

## Volatile instruments detection

TODO
