package com.feng.myhotel.utils;

import com.feng.myhotel.entities.vo.PermissionTree;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author : pcf
 * @date : 2021/11/20 20:00
 */
@Slf4j
public class TreeMenuUtils {

    public static List<PermissionTree> getPermissionTreeList(List<PermissionTree> permissionTreeList){

        // 遍历将其装入map中
        Map<String, PermissionTree> map = new HashMap<>();
        for (PermissionTree permissionTree : permissionTreeList) {

//            if (permissionTree.getPermissionName().equals("list")){
//                permissionTree.setPermissionName("查看");
//            }else if (permissionTree.getPermissionName().equals("update")){
//                permissionTree.setPermissionName("修改");
//            }else if (permissionTree.getPermissionName().equals("delete")){
//                permissionTree.setPermissionName("删除");
//            }else if (permissionTree.getPermissionName().equals("add")){
//                permissionTree.setPermissionName("增加");
//            }

            // 将所有元素放入map
            map.put(permissionTree.getId() + "", permissionTree);
        }

        // 再次循环付给各child值
        for (PermissionTree permissionTree : permissionTreeList) {

            // 如果pid不为则放入map对应的中
            if (permissionTree.getPid() != null) {

                // 取出父子树
                String key = permissionTree.getPid() + "";
                PermissionTree parentPermission = map.get(key);

                parentPermission.getChild().add(permissionTree);

                map.put(parentPermission.getId() + "", parentPermission);
            }
        }

        List<PermissionTree> list = new ArrayList<>();

        // 再次循取出所有pid为null的菜单
        for (PermissionTree permissionTree : permissionTreeList) {

            // 如果pid不为则放入map对应的中
            if (permissionTree.getPid() == null) {
                list.add(permissionTree);
            }
        }

        return list;
    }

}
