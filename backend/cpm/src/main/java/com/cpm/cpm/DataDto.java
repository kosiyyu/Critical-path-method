package com.cpm.cpm;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DataDto {
    private List<Node> nodes;
    private List<Link> links;
}
