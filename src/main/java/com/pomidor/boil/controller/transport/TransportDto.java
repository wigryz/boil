package com.pomidor.boil.controller.transport;

import java.util.List;

public record TransportDto(
    List<TransactionDto> transactions
) {
}
