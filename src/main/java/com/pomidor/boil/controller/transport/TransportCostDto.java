package com.pomidor.boil.controller.transport;

public record TransportCostDto(
    Long supplierId,
    Long recipientId,
    Double cost
) {
}
