package br.app.adv.main.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.app.adv.main.exception.ArgumentValueException;

import java.time.LocalDateTime;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public final ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException ex,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
				ErroExceptionMessageEnum.AuthenticationException.getDescription(),
				ErroExceptionMessageEnum.AuthenticationException.getId(), request.getDescription(false),
				HttpStatus.UNAUTHORIZED.value());

		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(SQLGrammarException.class)
	public final ResponseEntity<ErrorDetails> handleSQLGrammarException(SQLGrammarException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
				ErroExceptionMessageEnum.SQLGrammarException.getDescription(),
				ErroExceptionMessageEnum.SQLGrammarException.getId(), request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR.value());

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ArgumentValueException.class)
	public final ResponseEntity<ErrorResponse> setArgumentValueException(ArgumentValueException ex, WebRequest request) {
		ErrorDetails errorD = new ErrorDetails(ex.getData(), ex.getMensagem(), ex.getErrorId(), ex.getInput());
		return handleBadRequestResponseException(errorD, request);
	}
	/** Response Error Bad Request @Param ErrorDetails */
	private final ResponseEntity<ErrorResponse> handleBadRequestResponseException(ErrorDetails errorD, WebRequest request) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), errorD);
		return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
	}
}
