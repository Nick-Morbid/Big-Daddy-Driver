package com.daddy.demo.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo implements Serializable {
    private String name;
    private Integer upId;
    private Integer level;
    private String path;
}
