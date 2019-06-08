package com.tsu.xjq.service.impl;

import com.tsu.xjq.dao.AssistantDao;
import com.tsu.xjq.pojo.*;
import com.tsu.xjq.service.AssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AssistantServiceImpl
 * @Author Elv1s
 * @Date 2019/3/7 9:05
 * @Description:
 */
@Service
public class AssistantServiceImpl implements AssistantService {

    @Autowired
    AssistantDao assistantDao;
    @Override
    public Assistant getAssistant(Assistant assistant) {
        return assistantDao.getAssistant(assistant);
    }

    @Override
    public List<Grade> getGrades(String grade) {
        return assistantDao.getGrades(grade);
    }

    @Override
    public EasyUIDataGridResult getVacates(int page, int rows, String name,String grade) {
        List<VacateInfo> needVacates = new ArrayList<>();
        List<VacateInfo> allVacates = assistantDao.getAllVacates(name,grade);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        //设置总记录数
        result.setTotal(allVacates.size());
        //当前页首条对象的index
        int firstIndex = (page - 1) * rows;
        //当前页最后一个index
        int lastIndex = page * rows;
        //防止index>list.size
        if (lastIndex >= allVacates.size()) {
            lastIndex = allVacates.size();
        }
        for (int i = firstIndex; i < lastIndex; i++) {
            VacateInfo vacate = allVacates.get(i);
            needVacates.add(vacate);
        }
        //设置当前页显示的对象
        result.setRows(needVacates);
        return result;
    }

    @Override
    public EasyUIDataGridResult getPendingVacates(int page, int rows, String grade) {
        List<VacateInfo> needVacates = new ArrayList<>();
        List<VacateInfo> allVacates = assistantDao.getPendingVacates(grade);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        //设置总记录数
        result.setTotal(allVacates.size());
        //当前页首条对象的index
        int firstIndex = (page - 1) * rows;
        //当前页最后一个index
        int lastIndex = page * rows;
        //防止index>list.size
        if (lastIndex >= allVacates.size()) {
            lastIndex = allVacates.size();
        }
        for (int i = firstIndex; i < lastIndex; i++) {
            VacateInfo vacate = allVacates.get(i);
            needVacates.add(vacate);
        }
        //设置当前页显示的对象
        result.setRows(needVacates);
        return result;
    }

    @Override
    public List<VacateInfo> adoptVacates(String status, String[] ids, String name) {
        List<VacateInfo> vacateInfos = new ArrayList<>();
        for (String vid : ids) {
            Vacate vacate = assistantDao.getVacateByVid(vid);
            String agrees = vacate.getAgrees();
            agrees = name + "," + agrees;
            assistantDao.adoptVacate(status, vid,agrees);
            VacateInfo vacateInfo = assistantDao.getVacateinfoByVid(vid);
            vacateInfos.add(vacateInfo);
        }
        return vacateInfos;
    }

    @Override
    public void adoptVacate(String s, String vid) {
        assistantDao.adoptVacate1(s, vid);
    }
}
