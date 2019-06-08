package com.tsu.xjq.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.tsu.xjq.pojo.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExcelUtil
 * @Author Elv1s
 * @Date 2019/3/10 15:15
 * @Description: 处理excel
 */
public class ExcelUtil {

    /**
     * 用easyexcel获得数据
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static List<Object> getStudent(File file) throws FileNotFoundException {
        List<Object> datas = new ArrayList<>();
        InputStream inputStream = new FileInputStream(file);
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, List.class, listener);
            excelReader.read();
            datas = listener.getDatas();
        } catch (Exception e) {

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }

    /**
     * 将MultipartFile转换成File
     * @param ins
     * @param file
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据封装到一个list<student>中
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static List<Student> makeStudentList(File file) throws FileNotFoundException {
        List<Object> objects = getStudent(file);
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < objects.size(); i++) {
            Student student = new Student();
            if (i !=0){
                List l = (List)objects.get(i);
                String o = (String) l.get(0);
                int sid = Integer.parseInt(o);
                //第0列学号
                student.setSid(sid);
                //第一列 姓名
                student.setName((String) l.get(1));
                //第二列年级 null 之后会有
                //第三列 班级 null 之后会有
                //第四列老师id  之后会有
                //第五列密码
                student.setPassword((String) l.get(5));
                //第六列 性别
                student.setGender((String) l.get(6));
                //第七列宿舍
                student.setDormNum((String) l.get(7));
                //第八列邮箱
                student.setEmail((String) l.get(8));
                //第九列电话
                student.setTelephone((String) l.get(9));
                students.add(student);
            }

        }
        return students;
    }

    /**
     * 返回List<student>
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Student> getStudentList(MultipartFile file) throws IOException {
        File toFile = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        List<Student> studentList = makeStudentList(toFile);

        return studentList;
    }
}

