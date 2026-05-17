package com.knowlegdebase.shared.cqrs;

/**
 * Interface cho xử lý Command (không trả về kết quả).
 *
 * @param <C> Kiểu Command
 */
public interface CommandHandler<C extends Command> {
    
    void handle(C command);
}
