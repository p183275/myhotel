package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.vo.MenuVO;
import com.feng.myhotel.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/14 15:46
 */
@RestController
@Api(tags = "菜单控制器")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "/user/get/menu")
    @ApiOperation(value = "拿到登录者的菜单", httpMethod = "GET", notes = "调用接口请带token!!!")
    public CommonResult<List<MenuVO>> getLoginMenu(HttpServletRequest request){

        try {
            List<MenuVO> menuList = menuService.getLoginMenuByRoleId(request);
            return new CommonResult<>(200, "查询菜单成功", menuList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "查询菜单失败");
        }

    }

}
