package com.knowlegdebase.shared.cqrs;

public interface CommandGateway {
    
    void send(Command command);
    
    <R> R send(CommandWithResult<R> command);
}
