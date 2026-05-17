package com.knowlegdebase.shared.cqrs;

public interface QueryGateway {
    
    <R> R ask(Query<R> query);
}
