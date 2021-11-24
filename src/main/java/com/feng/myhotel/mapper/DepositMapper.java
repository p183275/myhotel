package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.po.CashierPayPO;
import com.feng.myhotel.entities.vo.DepositAndOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/18 15:25
 */
@Mapper
public interface DepositMapper {

    /**
     * 向数据库中插入押金
     * @param cashierPayPO 押金封装类
     */
    void insertCashierPay(@Param("cashierPayPO") CashierPayPO cashierPayPO);

    /**
     * 拿到押金订单封装类
     * @return 押金订单封装类
     */
    List<DepositAndOrderVO> selectDepositAndOrderVOByStatus(@Param("status")String status);

    /**
     * 通过押金id退还押金
     * @param depositId 押金id
     */
    void updateDepositStatusById(@Param("depositId")Long depositId);

    /**
     * 通过订单id删除押金表
     * @param idList idlist
     */
    void deleteDepositByOrderIdList(@Param("idList") List<Long> idList);
}
