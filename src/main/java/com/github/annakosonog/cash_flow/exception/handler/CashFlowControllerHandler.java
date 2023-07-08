package com.github.annakosonog.cash_flow.exception.handler;
import com.github.annakosonog.cash_flow.exception.CashFlowException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CashFlowControllerHandler {

    @ExceptionHandler(value = CashFlowException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> cashFlowExceptionResponseError(CashFlowException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
