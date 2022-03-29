package com.pomidor.boil.controller.dtos;

public record HappeningDto(
    Integer id,
    Double minHappenTime,
    Double maxHapperTime,
    Double reserveTime
) {
}
