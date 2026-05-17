package com.knowlegdebase.shared.cqrs;

/**
 * Interface cho xử lý Command (có trả về kết quả).
 *
 * @param <C> Kiểu Command
 * @param <R> Kiểu kết quả trả về
 */
public interface CommandWithResultHandler<C extends CommandWithResult<R>, R> {
    
    R handle(C command);
}
