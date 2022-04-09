package com.pomidor.boil.controller.transport.dtos;

import java.util.List;

public record TransportInputDto (
    List<SupplierDto> suppliers,
    List<RecipientDto> recipients,
    List<TransportCostDto> transportCosts
    ) {
}
