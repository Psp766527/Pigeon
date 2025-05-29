package com.daimlertrucksasia.it.dsc.pigeon.exceptions.e;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class GraphQLErrorResponse implements GraphQLError {
    private String message;
    private List<Object> path;
    private List<SourceLocation> locations;
    private String errorCode;
    private String details;
    private int status;
    private OffsetDateTime timestamp;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return locations != null ? locations : Collections.emptyList();
    }

    @Override
    public ErrorClassification getErrorType() {
        return switch (errorCode != null ? errorCode : "") {
            case "NOT_FOUND" -> graphql.ErrorType.DataFetchingException;
            case "VALIDATION_FAILED" -> graphql.ErrorType.ValidationError;
            default -> graphql.ErrorType.DataFetchingException;
        };
    }

    @Override
    public List<Object> getPath() {
        return path != null ? path : Collections.emptyList();
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of(
                "code", errorCode != null ? errorCode : "UNKNOWN",
                "details", details != null ? details : "",
                "status", status,
                "timestamp", timestamp != null ? timestamp.toString() : OffsetDateTime.now().toString()
        );
    }
}