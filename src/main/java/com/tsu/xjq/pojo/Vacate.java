package com.tsu.xjq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName vacate
 * @Author Elv1s
 * @Date 2019/2/28 16:13
 * @Description:
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacate {

    private String id;
    private String sid;
    private String status;
    private String time;
    private String reason;
    private String cycle;
    private int days;
    private  String agrees;

}
