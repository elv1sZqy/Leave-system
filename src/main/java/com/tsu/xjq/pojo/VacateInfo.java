package com.tsu.xjq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName VacateInfo
 * @Author Elv1s
 * @Date 2019/3/3 18:59
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class VacateInfo {
    private String id;
    private String sid;
    private String name;
    private String cycle;
    private String reason;
    private String telephone;
    private String email;
    private String time;
    private int days;
    private String agrees;

}
