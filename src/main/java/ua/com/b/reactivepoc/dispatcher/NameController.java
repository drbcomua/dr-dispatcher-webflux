package ua.com.b.reactivepoc.dispatcher;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NameController {
    @PostMapping("/name")
    public Flux<Name> namePost(@RequestBody Flux<Name> names) {
        return ReactiveStorage.dispatch(names);
    }

    @DeleteMapping("/name")
    public Mono<Void> nameDelete(@RequestBody Flux<Name> names) {
        return ReactiveStorage.remove(names);
    }
}
