package com.pomidor.boil.controller.transport.dtos;

public record TransportCostDto(
    Long supplierId,
    Long recipientId,
    Double cost
) {
}
