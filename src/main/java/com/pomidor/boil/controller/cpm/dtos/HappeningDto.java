package com.pomidor.boil.controller.cpm.dtos;

import java.util.List;
import java.util.Set;

public record HappeningDto(
    Integer id,
    Double minHappenTime,
    Double maxHappenTime,
    Double reserveTime,
    List<Integer> nextHappeningIds
) {
}