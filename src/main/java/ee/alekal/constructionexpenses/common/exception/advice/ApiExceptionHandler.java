package ee.alekal.constructionexpenses.common.exception.advice;

import ee.alekal.constructionexpenses.common.exception.base.CEBaseException;
import ee.alekal.constructionexpenses.common.response.CEResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CEBaseException.class)
    public ResponseEntity<CEResponse<?>> handleCEBaseException(CEBaseException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(CEResponse.builder().message(exception.getMessage()).build());
    }
}
