package com.finance.infra;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.finance.dtos.ExceptionDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ExceptionDTO> threatDuplicateEntry(DataIntegrityViolationException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO("User already exists.", "400");
    return ResponseEntity.badRequest().body(exceptionDTO);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ExceptionDTO> threat404(EntityNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDTO> threatGeneralException(Exception exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionDTO> threatGeneralException(IllegalArgumentException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

  @ExceptionHandler(IllegalIdentifierException.class)
  public ResponseEntity<ExceptionDTO> threatGeneralException(IllegalIdentifierException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionDTO> threatGeneralException(HttpMessageNotReadableException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMostSpecificCause().getMessage(), "500");
    return ResponseEntity.internalServerError().body(exceptionDTO);
  }

}
