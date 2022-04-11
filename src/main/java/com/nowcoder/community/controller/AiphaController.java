package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AiphaController {


    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest req, HttpServletResponse resp){
        //req
        System.out.println(req.getMethod());
        System.out.println(req.getServletPath());

        Enumeration<String> enumeration = req.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = req.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(req.getParameter("code"));

        //resp
        resp.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = resp.getWriter();) {
            writer.write("1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required=false,defaultValue="1")int current,
            @RequestParam(name = "limit",required = false,defaultValue = "10") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    @RequestMapping(path="/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    //post请求
    @RequestMapping(path="/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);

        return "success";
    }

    //响应html数据
    @RequestMapping(path = "/teacher", method=RequestMethod.GET)
    //不加@ResponseBody默认返回html
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path="/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        //底层自动配一个model对象，这里是引用它
        model.addAttribute("name","bjdx");
        model.addAttribute("age",80);
        return "/demo/view";//返回templates的路径
    }


    //响应JSON数据（异步请求时）
    //Java对象->JSON字符串->JS对象  衔接作用
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","zhangsan");
        emp.put("age",23);
        emp.put("salary",8000);
        return emp;
    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();


        Map<String,Object> emp = new HashMap<>();
        emp.put("name","zhangsan");
        emp.put("age",23);
        emp.put("salary",8000);
        list.add(emp);
        Map<String,Object> emp1 = new HashMap<>();
        emp1.put("name","lisi");
        emp1.put("age",24);
        emp1.put("salary",9000);
        list.add(emp1);
        Map<String,Object> emp2 = new HashMap<>();
        emp2.put("name","wangwu");
        emp2.put("age",25);
        emp2.put("salary",10000);
        list.add(emp2);

        return list;

    }


}
