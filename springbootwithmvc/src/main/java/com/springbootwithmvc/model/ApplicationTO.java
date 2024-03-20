package com.springbootwithmvc.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationTO {
    private Integer applicationId;

    private String applicationName;

    private String description;

    private String owner;
}
