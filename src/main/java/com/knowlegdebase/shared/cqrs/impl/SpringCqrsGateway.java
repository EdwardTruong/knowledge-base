package com.knowlegdebase.shared.cqrs.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.knowlegdebase.shared.cqrs.*;
import com.knowlegdebase.shared.cqrs.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class SpringCqrsGateway implements CqrsGateway, ApplicationContextAware, SmartInitializingSingleton {

    private ApplicationContext applicationContext;

    private final Map<Class<? extends Command>, CommandHandler> commandHandlers = new HashMap<>();
    private final Map<Class<? extends CommandWithResult>, CommandWithResultHandler> commandWithResultHandlers = new HashMap<>();
    private final Map<Class<? extends Query>, QueryHandler> queryHandlers = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        // Đăng ký Command Handlers
        String[] commandHandlerNames = applicationContext.getBeanNamesForAnnotation(CqrsCommandHandler.class);
        for (String name : commandHandlerNames) {
            CommandHandler handler = applicationContext.getBean(name, CommandHandler.class);
            CqrsCommandHandler annotation = applicationContext.findAnnotationOnBean(name, CqrsCommandHandler.class);
            if (annotation != null) {
                commandHandlers.put(annotation.value(), handler);
            }
        }

        // Đăng ký CommandWithResult Handlers
        String[] commandWithResultHandlerNames = applicationContext.getBeanNamesForAnnotation(CqrsCommandWithResultHandler.class);
        for (String name : commandWithResultHandlerNames) {
            CommandWithResultHandler handler = applicationContext.getBean(name, CommandWithResultHandler.class);
            CqrsCommandWithResultHandler annotation = applicationContext.findAnnotationOnBean(name, CqrsCommandWithResultHandler.class);
            if (annotation != null) {
                commandWithResultHandlers.put(annotation.value(), handler);
            }
        }

        // Đăng ký Query Handlers
        String[] queryHandlerNames = applicationContext.getBeanNamesForAnnotation(CqrsQueryHandler.class);
        for (String name : queryHandlerNames) {
            QueryHandler handler = applicationContext.getBean(name, QueryHandler.class);
            CqrsQueryHandler annotation = applicationContext.findAnnotationOnBean(name, CqrsQueryHandler.class);
            if (annotation != null) {
                queryHandlers.put(annotation.value(), handler);
            }
        }
    }

    @Override
    public void send(Command command) {
        CommandHandler handler = commandHandlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("Không tìm thấy CommandHandler cho " + command.getClass().getSimpleName());
        }
        handler.handle(command);
    }

    @Override
    public <R> R send(CommandWithResult<R> command) {
        CommandWithResultHandler handler = commandWithResultHandlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("Không tìm thấy CommandWithResultHandler cho " + command.getClass().getSimpleName());
        }
        return (R) handler.handle(command);
    }

    @Override
    public <R> R ask(Query<R> query) {
        QueryHandler handler = queryHandlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("Không tìm thấy QueryHandler cho " + query.getClass().getSimpleName());
        }
        return (R) handler.handle(query);
    }
}
