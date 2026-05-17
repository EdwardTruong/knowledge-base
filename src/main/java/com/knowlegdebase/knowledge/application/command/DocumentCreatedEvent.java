package com.knowlegdebase.knowledge.application.command;

import java.util.UUID;

/**
 * Sự kiện được phát ra khi một Document mới được lưu thành công vào cơ sở dữ liệu.
 * 
 * @param documentId ID của tài liệu vừa được tạo
 */
public record DocumentCreatedEvent(UUID documentId) {
}