package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.MD5;
import com.example.springboot.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.crypto.hash.Sha256Hash;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/login2")  //明文登录
//    public Result login2(@RequestBody UserDTO userDTO){
//        String username = userDTO.getUsername();
//        String password = userDTO.getPassword();
//
//        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
//            return Result.error(Constants.CODE_400,"参数错误");
//        }
//        UserDTO dto = userService.login2(userDTO);
//        return Result.success(dto);
//    }

    @PostMapping("/login")  //密文转换登录
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
//        password = new Sha256Hash(password).toHex();
//        userDTO.setPassword(password);

        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        //对密码进行MD5加密后存入数据库
        String password = MD5Util.code(userDTO.getPassword());
        userDTO.setPassword(password);

        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

//    @PostMapping("/reservation")
//    public void reservation(@RequestBody User user){
//        User result = userService.reservation(user);
//        System.out.println(result);
//    }

    //新增，修改
    @PostMapping
    public Result save(@RequestBody User user){
        return Result.success(userService.saveUser(user));
    }

    //查询
    @GetMapping
    public Result findAll(){
        return Result.success(userService.list());
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(userService.removeById(id));
    }



    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
    }

    //分页查询接口
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum , @RequestParam Integer pageSize, @RequestParam String username){
//        pageNum = (pageNum - 1)* pageSize;
//        username = "%" + username + "%";
//        List<User> data = userMapper.selectPage(pageNum,pageSize,username);
//        Integer total = userMapper.selectTotal(username);
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }

    //分页查询，mybatis-plus
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum ,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
//                                @RequestParam(defaultValue = "") String nickname,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address
                                ){

        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
//        if(!"".equals(nickname)){
//            queryWrapper.like("nickname",nickname);
//        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        queryWrapper.orderByDesc("id");

        return Result.success(userService.page(page,queryWrapper));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库获取数据
        List<User> list = userService.list();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","ID");
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("create_time","创建时间");
        writer.addHeaderAlias("avatar","头像");

        //一次性写出list中对象到Excel
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

//    导入
    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
//        List<User> list = reader.read(0,1,User.class);
//        System.out.println(list);
        userService.saveBatch(list);
        return true;
    }

}
