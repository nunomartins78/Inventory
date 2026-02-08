package inventory.controller;

import inventory.controller.dto.ApiErrorDto;
import inventory.domain.exception.BusinessRuleException;
import inventory.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleNotFound (NotFoundException e, HttpServletRequest request){
        ApiErrorDto body = new ApiErrorDto(
                Instant.now(),
                NOT_FOUND.value(),
                NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(NOT_FOUND).body(body);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity <ApiErrorDto> handleBusinessRule (BusinessRuleException e, HttpServletRequest request){
        ApiErrorDto body = new ApiErrorDto(
                Instant.now(),
                UNPROCESSABLE_ENTITY.value(),
                UNPROCESSABLE_ENTITY.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity <ApiErrorDto> handleGeneric (Exception e, HttpServletRequest request){
        ApiErrorDto body = new ApiErrorDto(
                Instant.now(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Erro inesperado",
                request.getRequestURI()
        );
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity <ApiErrorDto> handleValidator (MethodArgumentNotValidException e, HttpServletRequest request){
        ApiErrorDto body = new ApiErrorDto(
                Instant.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                "Pedido inválido (validação falhou)",
                request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity <ApiErrorDto> handleBadJson (HttpMessageNotReadableException e, HttpServletRequest request){
        ApiErrorDto body = new ApiErrorDto(
                Instant.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                "JSON inválido",
                request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(body);
    }
}
