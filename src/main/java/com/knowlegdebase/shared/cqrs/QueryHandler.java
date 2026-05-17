package com.knowlegdebase.shared.cqrs;

/**
 * Interface cho xử lý Query.
 *
 * @param <Q> Kiểu Query
 * @param <R> Kiểu kết quả trả về
 */
public interface QueryHandler<Q extends Query<R>, R> {
    
    R handle(Q query);
}
