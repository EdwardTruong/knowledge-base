/**
 * Spring Modulith Package Configuration for the Planning Module.
 * 
 * Mục đích của file này:
 * 1. Định nghĩa ranh giới Module (Module Boundaries): Biến package 'planning' thành một Application Module độc lập.
 * 2. Tài liệu tự động (Documentation): Thuộc tính 'displayName' giúp Spring Modulith sinh ra sơ đồ kiến trúc (C4 Model / UML) với tên dễ đọc.
 * 3. Kiểm soát kiến trúc (Architectural Enforcement): Thuộc tính 'allowedDependencies' đóng vai trò như "người lính gác".
 *    Nó đảm bảo module Planning CHỈ được phép gọi và phụ thuộc vào module 'knowledge' (ví dụ: gán Document vào Task).
 *    Nếu ai đó cố tình gọi sang các module khác, quá trình chạy Unit Test (ArchUnit) sẽ báo lỗi ngay lập tức,
 *    giúp ngăn chặn mã nguồn bị rối rắm (Spaghetti code).
 */
@org.springframework.modulith.ApplicationModule(
    displayName = "Planning & Task Module",
    allowedDependencies = {"knowledge"}
)
package com.knowlegdebase.planning;