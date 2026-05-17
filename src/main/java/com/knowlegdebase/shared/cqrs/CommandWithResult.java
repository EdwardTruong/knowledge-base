package com.knowlegdebase.shared.cqrs;

/**
 * Marker interface cho tất cả các Command có trả về kết quả (ví dụ: ID của entity vừa tạo).
 * Command được dùng để thay đổi trạng thái của hệ thống.
 *
 * @param <R> Kiểu dữ liệu trả về của Command
 */
public interface CommandWithResult<R> {
}
