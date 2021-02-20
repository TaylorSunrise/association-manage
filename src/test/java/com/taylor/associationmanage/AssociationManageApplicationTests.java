package com.taylor.associationmanage;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taylor.associationmanage.entity.AssoCategory;
import com.taylor.associationmanage.entity.Association;
import com.taylor.associationmanage.entity.Role;
import com.taylor.associationmanage.entity.User;
import com.taylor.associationmanage.enums.UserTypeEnum;
import com.taylor.associationmanage.mapper.AssoCategoryMapper;
import com.taylor.associationmanage.mapper.AssociationMapper;
import com.taylor.associationmanage.mapper.RoleMapper;
import com.taylor.associationmanage.mapper.UserMapper;
import com.taylor.associationmanage.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class AssociationManageApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AssociationMapper associationMapper;

    @Autowired
    private AssoCategoryMapper assoCategoryMapper;

    @Test
    void contextLoads() {

    }
    @Test
    void insertUser(){
        User user = new User();
        user.setUsername("admin5");
        user.setSalt(MD5Util.getSalt());
        user.setPassword(MD5Util.hashed("111", user.getSalt()));
        user.setName("admin5");
        user.setPhone("18894536254");
        user.setEmail("admin5@qq.com");
        user.setRoleId(UserTypeEnum.ADMIN.getValue());
        user.setSex("男");
        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }
    @Test
    void selectusername(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", "root");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
    @Test
    void selectuserRole(){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",1362972213573181441L).select("DISTINCT role");
        List<Role> roles = roleMapper.selectList(queryWrapper);
        roles.forEach(System.out::println);
    }
    @Test
    void insertRole1(){
        insertRole(1362972213573181441L, 1362972213573181441L, "admin");
    }
    void insertRole(long userId,long associationId,String roleStr){
        Role role = new Role();
        role.setUserId(userId);
        role.setAssociationId(associationId);
        role.setRole(roleStr);
        switch (roleStr){
            case "admin":
                role.setRoleName("社长");
                break;
            case "activity":
                role.setRoleName("活动组织部部长");
                break;
            case "notice":
                role.setRoleName("宣传部部长");
                break;
            case "finance":
                role.setRoleName("财务管理部部长");
                break;
        }
        roleMapper.insert(role);
    }
    @Test
    void insertAsso(){
        Association association = new Association();
        association.setAssociationName("轮滑社");
        association.setAssociationDescription("轮滑社");
        association.setAssociationCategory(1362960808576937986L);
        association.setType(1);
        association.setUserId(1362972354350800897L);
        association.setStatus(1);
        association.setApprovalTime(new Date());
        association.setApprovalId(1362709174345060354L);
        association.setLeaderId(1362972354350800897L);
        associationMapper.insert(association);

        insertRole(association.getUserId(), association.getAssociationId(), "admin");
    }

    @Test
    void insertCategory(){
        List<String> strings = new ArrayList<>();
        strings.add("科技学术类");
        strings.add("文艺体育类");
        strings.add("理论学习类");
        strings.add("公益服务类");
        for (String s :
                strings) {
            AssoCategory assoCategory=new AssoCategory();
            assoCategory.setName(s);
            assoCategory.setDescription(s);
            assoCategoryMapper.insert(assoCategory);
        }

    }

}
