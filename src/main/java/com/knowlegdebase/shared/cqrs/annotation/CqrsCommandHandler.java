package com.knowlegdebase.shared.cqrs.annotation;

import org.springframework.stereotype.Component;
import com.knowlegdebase.shared.cqrs.Command;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CqrsCommandHandler {
    
    // Định nghĩa class Command mà Handler này sẽ xử lý
    Class<? extends Command> value();
}
