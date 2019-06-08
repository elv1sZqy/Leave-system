package com.tsu.xjq.pojo;

/**
 * @ClassName EasyUIDataGridResult
 * @Author Elv1s
 * @Date 2019/1/14 13:23
 * @Description:
 */
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class EasyUIDataGridResult implements Serializable{

    private long total;
    private List rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }

}
