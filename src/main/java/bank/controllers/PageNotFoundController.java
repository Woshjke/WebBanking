package bank.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class PageNotFoundController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(Exception ex) {
        return new ModelAndView("404");
    }
}
