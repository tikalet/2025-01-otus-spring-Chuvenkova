package ru.otus.hw.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.hw.dto.ErrorDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handeNotFoundException(NotFoundException ex) {
        System.out.println("ERROR: " + ex.getMessage());

        ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handeMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        System.out.println("ERROR: " + ex.toString());

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        String errorMessage = allErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("." + System.lineSeparator()));

        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handeInternalError(Exception ex) {
        System.out.println("ERROR: " + ex.toString());

        ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal error");
        return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
