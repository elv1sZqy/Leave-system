package com.tsu.xjq.service;

import com.tsu.xjq.pojo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AssistantService {

    /**
     * 通过账号密码获得assistant对象,如果有则返回
     * @param assistant
     * @return
     */
    Assistant getAssistant(Assistant assistant);

    /**
     * 通过年级号查出该年级所有班级
     * @param grade
     * @return
     */
    List<Grade> getGrades(String grade);

    /**
     * 获得一个班级的所有请假记录
     * @param page
     * @param rows
     * @param name
     * @param grade
     * @return
     */
    EasyUIDataGridResult getVacates(int page, int rows, String name,String grade);

    /**
     * 获得一个年级待处理的请求
     * @param page
     * @param rows
     * @param grade
     * @return
     */
    EasyUIDataGridResult getPendingVacates(int page, int rows, String grade);

    /**
     * 处理一个或者多个请假请求 并返回相对应的请求信息对象
     * @param status
     * @param ids
     * @param assistantName
     */
    List<VacateInfo> adoptVacates(String status, String[] ids, String assistantName);

    /**
     * 大于7天  设置status为6
     * @param s
     * @param vid
     */
    void adoptVacate(String s, String vid);
}
