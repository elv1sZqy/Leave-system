package com.tsu.xjq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName Student
 * @Author Elv1s
 * @Date 2019/1/14 13:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Student {
    private int sid;
    private String name;
    private String password;
    private Teacher teacher;
    private String dormNum;
    private String className;
    private String grade;
    private String email;
    private String telephone;
    private String gender;
}
