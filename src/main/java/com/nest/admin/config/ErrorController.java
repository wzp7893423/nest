package com.nest.admin.config;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wzp on 2018/5/15.
 */
@RestController
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }
}
