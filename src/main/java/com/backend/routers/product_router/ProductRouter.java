package com.backend.routers.product_router;

import com.backend.collections.Product;
import com.backend.use_case.product.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRouter {
    Logger log = LoggerFactory.getLogger("QueryRouter");

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllProduct", summary = "Get all products", tags = "Product",
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            })}
    ))
    public RouterFunction<ServerResponse> getAllProducts(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/products").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.get(), Product.class))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "Get product", summary = "Get product", tags = "Product",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id",
                            description = "Product id",
                            required = true)},
            responses = {@ApiResponse(responseCode = "200", description = "Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))
                    })}
    ))
    public RouterFunction<ServerResponse> getProduct(GetProductUseCase getProductUseCase) {
        return route(GET("/products/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getProductUseCase.apply(request.pathVariable("id")),
                                Product.class))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "Create product", summary = "Create product", tags = "Product",
            responses = {@ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            })},
            requestBody = @RequestBody(required = true, description = "Add product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))
                    })
    ))
    public RouterFunction<ServerResponse> createTeam(CreateProductUseCase createProductUseCase) {
        return route(POST("/products").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Product.class)
                        .flatMap(createdProduct -> createProductUseCase.apply(createdProduct)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "Update product", summary = "Update product", tags = "Product",
            responses = {@ApiResponse(responseCode = "200", description = "Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            })},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id",
                    description = "Product id",
                    required = true)},
            requestBody = @RequestBody(required = true, description = "Add product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))
                    })

    ))
    public RouterFunction<ServerResponse> updateProduct(UpdateProductUseCase updateProductUseCase) {
        return route(PUT("/products/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Product.class)
                        .flatMap(updateProduct -> updateProductUseCase.apply(request.pathVariable("id"), updateProduct)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "Delete product", summary = "Delete product", tags = "Product",
            responses = {@ApiResponse(responseCode = "200", description = "deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            })},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id",
                    description = "Product id",
                    required = true)}
    ))
    public RouterFunction<ServerResponse> deleteTeam(DeleteProductUseCase deleteProductUseCase) {
        return route(DELETE("/products/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteProductUseCase.apply(request.pathVariable("id")), String.class))
        );
    }
}
