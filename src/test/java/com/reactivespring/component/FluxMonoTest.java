package com.reactivespring.component;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxMonoTest {

    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring Boot")
                //.concatWith(Flux.error(new RuntimeException("Exception Occured")))
                .concatWith(Flux.just("after error"))
                .log();
        stringFlux.subscribe(System.out::println,
                (e) -> System.err.println("exception is" + e),
                () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElement_successTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring Boot")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring Boot")
                .verifyComplete();
    }

    @Test
    public void fluxTestElement_errorTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring Boot")
                .concatWith(Flux.error(new RuntimeException()))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring Boot")
                .verifyError();
    }

    @Test
    public void fluxTestElement_errorTest2(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring Boot")
                .concatWith(Flux.error(new RuntimeException("Error Flux")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Error Flux")
                .verify();
    }

    @Test
    public void mono_successTest(){
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void mono_errorTest(){
        Mono<Object> stringMono = Mono.error(new RuntimeException("Mono Error"))
                .log();
        StepVerifier.create(stringMono)
                .expectErrorMessage("Mono Error")
                .verify();
    }
}
