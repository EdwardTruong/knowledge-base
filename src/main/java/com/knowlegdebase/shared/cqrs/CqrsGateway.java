package com.knowlegdebase.shared.cqrs;

/**
 * Facade tổng hợp cho CQRS, controller chỉ cần inject `CqrsGateway` là đủ.
 */
public interface CqrsGateway extends CommandGateway, QueryGateway {
}
