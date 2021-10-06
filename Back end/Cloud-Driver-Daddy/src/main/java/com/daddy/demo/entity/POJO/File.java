package com.daddy.demo.entity.POJO;

import com.daddy.demo.entity.BO.Files;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {
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
    private String path;

    public File(Files file) {
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
    }
}
