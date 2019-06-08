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
public class LayuiResult implements Serializable{

    private String code;
    private String msg;
    private long count;
    private List data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
