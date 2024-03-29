package com.cpm.cpm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Link {
    private int source;
    private int target;
    private String name;
    private String type;
}
