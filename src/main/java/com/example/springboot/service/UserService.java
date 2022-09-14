package com.example.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Constants;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.MD5;
import com.example.springboot.utils.MD5Util;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;

    public boolean saveUser(User user) {
//        if(user.getId() == null){
//            return save(user);
//        }else{
//            return updateById(user);
//        }
        return saveOrUpdate(user);

    }

    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one != null){
            BeanUtil.copyProperties(one, userDTO, true);
            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
        }else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;
    }

    public User reservation(User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User one = getOne(queryWrapper);

//        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        lambdaUpdateWrapper.eq(User::getUsername, user.getUsername())
//                .set(User::getReservation, user.getReservation());
//        userMapper.update(null, lambdaUpdateWrapper);


//        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
//        userUpdateWrapper.eq("username",user.getUsername());
//        userUpdateWrapper.set("reservation",user.getReservation());
//        userMapper.update(null,userUpdateWrapper);

//        int result = userMapper.update(user, userUpdateWrapper);
//        System.out.println(result);
//        one.setReservation(userDTO.getReservation());
        return one;
    }

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",MD5.string2MD5(userDTO.getPassword()));  //输入密码转换成MD5密文，并与数据库内密码比对
        User one;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

//    private User getUserInfo2(UserDTO userDTO){
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",userDTO.getUsername());
//        queryWrapper.eq("password",userDTO.getPassword());
//        User one;
//        try{
//            one = getOne(queryWrapper);
//        }catch (Exception e){
//            throw new ServiceException(Constants.CODE_500, "系统错误");
//        }
//        return one;
//    }
//
//    public UserDTO login2(UserDTO userDTO) {
//        User one = getUserInfo2(userDTO);
//        if(one != null){
//            BeanUtil.copyProperties(one, userDTO, true);
//            return userDTO;
//        }else{
//            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
//        }
//    }

//    @Autowired
//    private UserDTO userDTO;
//
//    @Override
//    public User checkUser(String username, String password) {
//        return userDTO.findByUsernameAndPassword(username, MD5Util.code(password));//加密 只需要在业务层就可以
//    }

//    @Autowired
//    private UserMapper userMapper;

//    public int save(User user){
//        if(user.getId() == null){  //user无ID表示为新增
//            return userMapper.insert(user);
//        }else{  //user有ID表示更新
//            return userMapper.update(user);
//        }
//    };
}

