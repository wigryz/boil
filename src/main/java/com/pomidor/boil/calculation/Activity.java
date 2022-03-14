package com.pomidor.boil.calculation;

public record Activity (
    String name,
    Double duration,
    Integer previousHappeningId,
    Integer nextHappeningId
) {
}
