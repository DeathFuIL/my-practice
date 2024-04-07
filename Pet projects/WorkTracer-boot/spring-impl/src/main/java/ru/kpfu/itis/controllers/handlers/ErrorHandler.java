package ru.kpfu.itis.controllers.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.exceptions.controller.PageNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequestMapping("/error")
public class ErrorHandler extends AbstractErrorController {

    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

   @GetMapping
   public ResponseEntity<?> handleErrors(HttpServletRequest request) {
       HttpStatus status = getStatus(request);

       if (status.equals(HttpStatus.NOT_FOUND)) {
           throw new PageNotFoundException();
       }

       return ResponseEntity.status(status).build();
   }


}
