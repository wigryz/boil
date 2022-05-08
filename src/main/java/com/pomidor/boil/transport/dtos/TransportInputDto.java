package com.pomidor.boil.transport.dtos;

import java.util.List;

public record TransportInputDto (
    List<SupplierDto> suppliers,
    List<RecipientDto> recipients,
    List<TransportCostDto> transportCosts
    ) {
}
