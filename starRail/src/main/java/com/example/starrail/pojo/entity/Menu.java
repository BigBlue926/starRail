package com.example.starrail.pojo.entity;

import lombok.Data;

@Data
public class Menu {
    Integer id;//菜单id
    String name;//菜单名字
    String path;//导航路径
    String component;//导航组件
    String icon;//导航图标
    Integer orderNumber;//排序
    Integer parentId;//父类id
    String pathName;//路径名称
}
