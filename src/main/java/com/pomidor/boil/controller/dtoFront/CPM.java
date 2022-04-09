package com.pomidor.boil.controller.dtoFront;

import java.util.List;

public record CPM (
    Elements elements,
    List<Tippy> tippies,
    List<String> criticalPathIds,
    Double criticalPathLength
){}
