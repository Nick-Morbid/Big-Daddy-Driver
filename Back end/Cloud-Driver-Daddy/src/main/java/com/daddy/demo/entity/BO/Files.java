package com.daddy.demo.entity.BO;

import com.daddy.demo.entity.POJO.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files implements Serializable {
    private Integer id;
    private String name;
    private Integer state;
    private String suffix;
    private Integer level;
    private Integer upId;
    private Date buildtime;
    private Date deletetime;
    private Integer uId;
    private String type;
    private List<Files> children;

    public Files(File file){
        id = file.getId();
        name = file.getName();
        state = file.getState();
        suffix = file.getSuffix();
        level = file.getLevel();
        upId = file.getUpId();
        buildtime = file.getBuildtime();
        deletetime = file.getDeletetime();
        uId = file.getUId();
        type = file.getType();
        children = new LinkedList<>();
    }

}
