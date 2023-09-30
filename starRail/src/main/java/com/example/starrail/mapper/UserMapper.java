package com.example.starrail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.starrail.pojo.entity.Menu;
import com.example.starrail.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
     @Select("select * from user where username = #{username} and password = #{password}")
     User selectByUsernameAndPassword(User user);

     @Select("select role_id from user_role_link where user_id = #{userId}")
     Integer getUserRoleId(Integer userId);

     @Select("select m.id ,m.name,m.path,m.component,m.icon,m.order_num,m.parent_id\n" +
             "from role_menu r left join menu m\n" +
             "on r.menu_id  = m.id\n" +
             "where role_id = #{ id }")
     List<Menu> getUserMenu(Integer id);
}
