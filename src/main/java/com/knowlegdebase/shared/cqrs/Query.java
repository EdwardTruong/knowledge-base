package com.knowlegdebase.shared.cqrs;

/**
 * Marker interface cho tất cả các Query.
 * Query được dùng để lấy dữ liệu (Read-only) và không bao giờ thay đổi trạng thái hệ thống.
 *
 * @param <R> Kiểu dữ liệu trả về của Query (Thường là DTO hoặc Page<DTO>)
 */
public interface Query<R> {
}
