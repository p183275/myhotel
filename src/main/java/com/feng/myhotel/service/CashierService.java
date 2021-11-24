package com.feng.myhotel.service;

import com.feng.myhotel.entities.vo.CashierPayVO;
import com.feng.myhotel.entities.vo.DepositAndOrderVO;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/18 15:36
 */
public interface CashierService {

    /**
     * 拿到未付款的订单
     * @return 未付款订单列表
     */
    PageInfo<RoomOrderVO> getUnPayOrder(Integer pageIndex, Integer pageSize, String payStatus);

    /**
     * 生成押金
     * @param cashierPayVO 押金封装类
     */
    void createDeposit(CashierPayVO cashierPayVO);

    /**
     * 拿到为退款的押金列表
     * @param pageIndex 页码
     * @param pageSize 页面最大信息量
     * @return 封装列表
     */
    PageInfo<DepositAndOrderVO> getDepositAndOrderVO(Integer pageIndex, Integer pageSize, String status);

    /**
     * 根据押金id退还押金
     * @param map 押金id
     */
    void giveBackDeposit(Map<String, Long> map);

}
