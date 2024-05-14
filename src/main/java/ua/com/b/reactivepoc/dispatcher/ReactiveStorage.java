package ua.com.b.reactivepoc.dispatcher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReactiveStorage {
    private static final Sinks.Many<Name> NAME_SINK = Sinks.many().unicast().onBackpressureBuffer();
    private static final Set<Name> storedNames = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static Flux<Name> dispatch(Flux<Name> names) {
        return names.flatMap(name -> {
            if (storedNames.contains(name)) {
                return Flux.just(name);
            } else {
                storedNames.add(name);
                return Flux.empty();
            }
        }).doOnNext(NAME_SINK::tryEmitNext);
    }

    public static Mono<Void> remove(Flux<Name> names) {
        return names.doOnNext(name -> {
            storedNames.remove(name);
            NAME_SINK.tryEmitNext(name);
        }).then();
    }

    public static void reset() {
        storedNames.clear();
    }

    private ReactiveStorage() {}
}
