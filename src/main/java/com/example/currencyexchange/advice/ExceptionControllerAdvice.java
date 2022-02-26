package com.example.currencyexchange.advice;

import com.example.currencyexchange.domain.response.ApiError;
import com.example.currencyexchange.domain.response.ApiSubError;
import com.example.currencyexchange.domain.response.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionControllerAdvice.class);
    public static final String BINDING_ERROR = "Validation has failed";

    private static final String SQL_UNIQUE_VIOLATION_CODE = "23505";
    private static final String SQL_DUP_EXCEP_PREFIX = "duplicate key value violates unique constraint";
    private static final String SQL_DUP_EXCEP_PREFIX_ES = "llave duplicada viola restricción de unicidad";
    private static final String TEMP_UNIQUE_CONS_ONE = "uk_ou8q9939fa4k88wjh17qwcmre";
    private static final String TEMP_UNIQUE_CONS_TWO = "uk_h84pd2rtr12isnifnj655rkra";
    private static final String TEMP_UC_ONE_MSG = "¡El nombre de la moneda ya esta registrado, agregar otro!";
    private static final String TEMP_UC_TWO_MSG = "¡El codigo de la moneda ya esta registrado, agregar otro!.";


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ApiError> handleBindException(WebExchangeBindException ex) {
        ex.getModel().entrySet().forEach(e -> {
            LOGGER.warn(e.getKey() + ": " + e.getValue());
        });
        List<ApiSubError> errors = new ArrayList<>();

        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ApiSubError(x.getObjectName(), x.getField(), x.getRejectedValue(), x.getDefaultMessage()));
        }
        return Mono.just(new ApiError(HttpStatus.BAD_REQUEST, BINDING_ERROR, errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ApiError> handleBindException(IllegalArgumentException ex) {
        return Mono.just(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundValidationException.class)
    public @ResponseBody
    Mono<ApiError> handlerNotFoundValidationException(NotFoundValidationException ex) {
        return Mono.just(new ApiError(HttpStatus.NOT_FOUND, ex));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public @ResponseBody
    Mono<ErrorResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String mostSpecCause = ex.getMostSpecificCause().toString();
        LOGGER.warn(mostSpecCause);
        int traceLen = ex.getStackTrace().length;
        for (int i = 0; i < 7; i++) {
            if (traceLen < 7 && i == traceLen) {
                break;
            }
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }

        if (mostSpecCause.contains(SQL_DUP_EXCEP_PREFIX) || mostSpecCause.contains(SQL_DUP_EXCEP_PREFIX_ES)) {
            if (mostSpecCause.contains(TEMP_UNIQUE_CONS_ONE)) {
                return Mono.just(new ErrorResponse(TEMP_UC_ONE_MSG, SQL_UNIQUE_VIOLATION_CODE));
            }

            if (mostSpecCause.contains(TEMP_UNIQUE_CONS_TWO)) {
                return Mono.just(new ErrorResponse(TEMP_UC_TWO_MSG, SQL_UNIQUE_VIOLATION_CODE));
            }
        }

        return Mono.just(new ErrorResponse(mostSpecCause, SQL_UNIQUE_VIOLATION_CODE));
    }
}


