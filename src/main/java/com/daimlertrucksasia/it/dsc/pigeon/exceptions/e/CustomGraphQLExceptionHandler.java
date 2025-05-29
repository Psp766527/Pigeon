package com.daimlertrucksasia.it.dsc.pigeon.exceptions.e;

import com.daimlertrucksasia.it.dsc.pigeon.kafka.service.producer.PigeonKafkaProducerService;
import com.daimlertrucksasia.it.dsc.pigeon.localization.service.MessageService;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketException;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

import jakarta.validation.ValidationException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.concurrent.CompletableFuture;

import static io.leangen.graphql.util.ClassFinder.log;

@Component
public class CustomGraphQLExceptionHandler implements DataFetcherExceptionHandler {

    @Autowired
    private final MessageService messageService;

    @Autowired
    private final PigeonKafkaProducerService pigeonKafkaProducerService;

    private final Map<Class<? extends Throwable>, Function<DataFetcherExceptionHandlerParameters, GraphQLErrorResponse>> handlers;

    public CustomGraphQLExceptionHandler(MessageService messageService, PigeonKafkaProducerService pigeonKafkaProducerService) {

        this.messageService = messageService;
        this.pigeonKafkaProducerService = pigeonKafkaProducerService;


        handlers = new HashMap<>();
        Function<DataFetcherExceptionHandlerParameters, GraphQLErrorResponse> notFound = handlers.put(ResourceNotFoundException.class, params -> GraphQLErrorResponse.builder()
                .message(params.getException().getMessage())
                .status(400)
                .errorCode("NOT_FOUND")
                .details("The requested resource was not found")
                .path(params.getPath().toList())
                .locations(List.of(params.getSourceLocation()))
                .timestamp(OffsetDateTime.now())
                .build());
        handlers.put(ValidationException.class, params -> GraphQLErrorResponse.builder()
                .message(messageService.getMessage(params.getException().getMessage() != null ? params.getException().getMessage() :
                        "In-Valid GraphQL Query/Mutation", Locale.ENGLISH))
                .status(400)
                .errorCode("VALIDATION_FAILED")
                .details("New Details")
                .path(params.getPath().toList())
                .locations(List.of(params.getSourceLocation()))
                .timestamp(OffsetDateTime.now())
                .build());
        handlers.put(MongoSocketException.class, params -> GraphQLErrorResponse.builder()
                .message(messageService.getMessage(params.getException().getMessage() != null ? params.getException().getMessage() :
                        "Error while Executing DB Query", Locale.ENGLISH))
                .status(400)
                .errorCode("VALIDATION_FAILED")
                .details("New Details")
                .path(params.getPath().toList())
                .locations(List.of(params.getSourceLocation()))
                .timestamp(OffsetDateTime.now())
                .build());
        handlers.put(MongoException.class, params -> GraphQLErrorResponse.builder()
                .message(messageService.getMessage(params.getException().getMessage() != null ? params.getException().getMessage() :
                        "Error while Executing DB Query", Locale.ENGLISH))
                .status(400)
                .errorCode("VALIDATION_FAILED")
                .details("New Details")
                .path(params.getPath().toList())
                .locations(List.of(params.getSourceLocation()))
                .timestamp(OffsetDateTime.now())
                .build());
        handlers.put(Throwable.class, params -> GraphQLErrorResponse.builder()
                .message(messageService.getMessage(params.getException().getMessage() != null ? params.getException().getMessage() :
                        "Internal Server Error", Locale.ENGLISH)).status(400)
                .errorCode("INTERNAL_SERVER_ERROR")
                .details("Nothing to Show")
                .path(params.getPath().toList())
                .locations(List.of(params.getSourceLocation()))
                .timestamp(OffsetDateTime.now())
                .build());
    }

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters parameters) {
        Throwable exception = parameters.getException();
        log.error("GraphQL error: {}", exception.getMessage(), exception);

        GraphQLErrorResponse error = handlers.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(exception))
                .findFirst()
                .map(entry -> entry.getValue().apply(parameters))
                .orElse(handlers.get(Throwable.class).apply(parameters));

        DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
                .error(error)
                .build();

        return CompletableFuture.completedFuture(result);
    }
}