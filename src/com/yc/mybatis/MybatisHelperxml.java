package com.yc.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yc.bean.Student;

public class MybatisHelperxml {
    public static void main(String[] args) {
        MybatisHelperxml mh = new MybatisHelperxml();
        //mh.findStudent();
        //mh.updateStu();
        mh.findStr();
    }

    //一级缓存，是session级别的：如果多次查询的语句相同，并且没有清空缓存，就会从一级缓存中拿语句查询，
    public void findStudent() {
        // 通过session工厂得到一个session
        SqlSession session = MybatisUtils.getSqlSession();
        try {
            // session中有很多操作方法
            Student s = new Student();
            //s.setSname("张三3");
            s.setSage(20);
            //s.setSex(1);
            // 会由mybatis框架自动根据你的usersMapper.xml中的select id为selectUser
            List<Student> c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }

            System.out.println("-------------------------------------------");
            //不关闭session查询；
            s.setSage(20);
            c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }
            
            System.out.println("-------------------------------------------");
            //清空缓存查询；
            session.clearCache();//清空缓存数据
            c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }
            
            
            System.out.println("-------------------------------------------");
            //刷新session
            session.close();  //
            session = MybatisUtils.getSqlSession();
            c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }
            
        } finally {
            session.close();
        }
    }
    
    
    //第二种清空缓存的时机：当发出更新操作时
    public void updateStu() {
        // 通过session工厂得到一个session
        SqlSession session = MybatisUtils.getSqlSession();
        try {
            // session中有很多操作方法
            Student s = new Student();
            s.setSid(4);
            
            List<Student> c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }
            //缓存测试
            c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }
            
            //更新数据
            s.setSid(4);
            s.setSname("张三" + 3);
            s.setSage(20);
            s.setSex(1);
            // 会由mybatis框架自动根据你的usersMapper.xml中的select id为selectUser
            int count = session.update("com.yc.dao.mapper.StudentMapper.updateStudent", s);
            System.out.println(s.getSid() + "\t" + s);
            session.commit();
            
            //发出同样的语句测试有没有缓存？
            s = new Student();
            s.setSid(4);
            
            c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }
        } finally {
            session.close();
        }
    }
    
  //二级缓存的使用：为二级缓存是跨session的
    public void findStr() {
        // 通过session工厂得到一个session
        SqlSession session = MybatisUtils.getSqlSession();
        SqlSession session2 = MybatisUtils.getSqlSession();
        session.clearCache();
        try {
            // session中有很多操作方法
            Student s = new Student();
            s.setSage(20);
            List<Student> c = session.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : c) {
                System.out.println(student);
            }
           
            session.close();
            
            // 会由mybatis框架自动根据你的usersMapper.xml中的select id为selectUser
            List<Student> cc = session2.selectList("com.yc.dao.mapper.StudentMapper.findStudentToWhere", s);
            for (Student student : cc) {
                System.out.println(student);
            }
            
        } finally {
            session2.close();
        }
    }
}
