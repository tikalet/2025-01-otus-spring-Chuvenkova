package ru.otus.hw.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handeNotFoundException(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("errorText", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ModelAndView handeAuthorizationDeniedException(AuthorizationDeniedException ex) {
        log.error("", ex);

        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.FORBIDDEN);
        modelAndView.addObject("errorText", "Access Denied");

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handeInternalError(Exception ex) {
        log.error("", ex);

        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("errorText", "Internal error");

        return modelAndView;
    }

}
