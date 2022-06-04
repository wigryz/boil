package com.pomidor.boil.cpm.dtoFront;

import java.util.List;

public record Elements (
        List<Node> nodes,
        List<Edge> edges
) {
}
