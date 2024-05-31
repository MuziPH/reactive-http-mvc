package io.glitchtech.reactive_http_mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;

@SpringBootApplication
public class ReactiveHttpMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveHttpMvcApplication.class, args);
    }

}

//==================CONTROLLER===========================================

@RestController
@RequiredArgsConstructor
class GreetingsController {

    private final GreetingService greetingService;

    @GetMapping("/greeting/{name}")
    Mono<GreetingResponse> greet(@PathVariable("name") String name) {
        return this.greetingService.greetingResponseMono(new GreetingsRequest(name));
    }
}


//==================SERVICE LAYER=========================================

// Service to produce GreetingResponse and GreetingsRequest when requested
@Service
class GreetingService {

    // Business logic to create a Greeting response
    private GreetingResponse greet(String name) {
        return new GreetingResponse("Hello " + name + " at " + Instant.now());
    }

    // Pass the name from the request
    Mono<GreetingResponse> greetingResponseMono(GreetingsRequest greetingsRequest) {
        return Mono.just(greet(greetingsRequest.getName()));
    }
}


//===================MODEL-ENTITY=========================================

// Model for Greetings response
@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingResponse {
    private String message;
}

//Model for Greetings request
@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingsRequest {
    private String name;
}


