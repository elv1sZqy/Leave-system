package com.tsu.xjq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName Admin
 * @Author Elv1s
 * @Date 2019/3/10 12:33
 * @Description:
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private String id;
    private String name;
    private String password;
    private String tele;
}
