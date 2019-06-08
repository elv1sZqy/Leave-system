package com.tsu.xjq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName Grade
 * @Author Elv1s
 * @Date 2019/3/7 14:35
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Grade {
    private Integer gid;
    private String  grade;
    private String name;

}
