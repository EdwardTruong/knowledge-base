package com.knowlegdebase.shared.cqrs.annotation;

import org.springframework.stereotype.Component;
import com.knowlegdebase.shared.cqrs.Query;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CqrsQueryHandler {
    
    // Định nghĩa class Query mà Handler này sẽ xử lý
    Class<? extends Query<?>> value();
}
