package com.backend.routers.purchase_router;

import com.backend.collections.Product;
import com.backend.collections.Purchase;
import com.backend.use_case.product.CreateProductUseCase;
import com.backend.use_case.product.GetAllProductsUseCase;
import com.backend.use_case.purchase.BuyProductsUseCase;
import com.backend.use_case.purchase.GetAllPurchasesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PurchaseRouter {
    Logger log = LoggerFactory.getLogger("QueryRouter");

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllPurchases", summary = "Get all purchases", tags = "Purchase",
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Purchase.class))
            })}
    ))
    public RouterFunction<ServerResponse> getAllPurchases(GetAllPurchasesUseCase getAllPurchasesUseCase) {
        return route(GET("/purchases").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllPurchasesUseCase.get(), Purchase.class))
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(operationId = "buyProducts", summary = "Buy products", tags = "Purchase",
            responses = {@ApiResponse(responseCode = "200", description = "Bought", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            })},
            requestBody = @RequestBody(required = true, description = "Add purchase",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Purchase.class))
                    })
    ))
    public RouterFunction<ServerResponse> buyProducts(BuyProductsUseCase buyProductsUseCase) {
        return route(POST("/purchases").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Purchase.class)
                        .flatMap(createdProduct -> buyProductsUseCase.apply(createdProduct)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))


        );
    }
}
