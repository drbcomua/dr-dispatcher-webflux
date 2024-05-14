package ua.com.b.reactivepoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ua.com.b.reactivepoc.dispatcher.Name;
import ua.com.b.reactivepoc.dispatcher.ReactiveStorage;

class ReactivePocIntegrationTests {
	Name D = new Name("D", "R");
	Name E = new Name("E", "F");

	@BeforeEach
	void setUp() {
		// Reset the state of the storage before each test
		ReactiveStorage.reset();
	}

	@Test
	void singleItemTest() {
		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D)))
				.expectComplete()
				.verify();
	}

	@Test
	void repeatSingleItemTest() {

		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D)))
				.expectComplete()
				.verify();

		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D)))
				.expectNext(D)
				.expectComplete()
				.verify();
	}

	@Test
	void twoItemsTest() {
		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D, E)))
				.expectComplete()
				.verify();
	}

	@Test
	void repeatTwoItemsTest() {
		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D, E)))
				.expectComplete()
				.verify();

		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D, E)))
				.expectNext(D, E)
				.expectComplete()
				.verify();
	}

	@Test
	void repeatTwoItemsDeleteFirstTest() {
		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D, E)))
				.expectComplete()
				.verify();

		StepVerifier.create(ReactiveStorage.remove(Flux.just(D)))
				.expectComplete()
				.verify();

		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D, E)))
				.expectNext(E)
				.expectComplete()
				.verify();
	}

	@Test
	void repeatTwoItemsDeleteSecondTest() {
		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D, E)))
				.expectComplete()
				.verify();

		StepVerifier.create(ReactiveStorage.remove(Flux.just(E)))
				.expectComplete()
				.verify();

		StepVerifier.create(ReactiveStorage.dispatch(Flux.just(D, E)))
				.expectNext(D)
				.expectComplete()
				.verify();
	}

}
