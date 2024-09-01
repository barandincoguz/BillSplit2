package com.BillSplit.blsplt_backend.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UniqueConstraintViolationException {
    private String message;
    private String details;




}
