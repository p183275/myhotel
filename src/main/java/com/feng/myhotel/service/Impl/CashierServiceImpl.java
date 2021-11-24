package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.constant.StatusConstant;
import com.feng.myhotel.entities.po.CashierPayPO;
import com.feng.myhotel.entities.vo.CashierPayVO;
import com.feng.myhotel.entities.vo.DepositAndOrderVO;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.feng.myhotel.mapper.DepositMapper;
import com.feng.myhotel.mapper.RoomMapper;
import com.feng.myhotel.mapper.RoomOrderPOMapper;
import com.feng.myhotel.service.CashierService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/18 15:36
 */
@Service
public class CashierServiceImpl implements CashierService {

    @Autowired
    private RoomOrderPOMapper roomOrderPOMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private DepositMapper depositMapper;

    /**
     * 拿到未付款的订单
     * @return 订单列表
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<RoomOrderVO> getUnPayOrder(Integer pageIndex, Integer pageSize, String payStatus) {

        // 设置分页
        PageHelper.startPage(pageIndex, pageSize);

        // 拿到未付款的订单列表
        List<RoomOrderVO> OrderVOList = roomOrderPOMapper.selectUnPayReserveRoomOrder(payStatus);

        // 封装分页工具
        PageInfo<RoomOrderVO> pageInfo = new PageInfo<>(OrderVOList);

        return pageInfo;
    }

    /**
     * 将押金存入数据
     * @param cashierPayVO 押金封装类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createDeposit(CashierPayVO cashierPayVO) {

        // 拿出订单的主键
        Long roomOrderId = cashierPayVO.getRoomOrderId();

        // 拿出金额
        BigDecimal deposit = cashierPayVO.getDeposit();

        // 判断
        if (roomOrderId == null || deposit == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 创建po对象并复制属性;
        CashierPayPO cashierPayPO = new CashierPayPO();
        BeanUtils.copyProperties(cashierPayVO, cashierPayPO);

        // 定义付款状态为1已付款 0--未付款 1--已付款
        // 定义订单状态为1正在生效 0--未生效 1--正在生效 2--已结束
        String payStatus = StatusConstant.ORDER_PAY_STATUS_PAY;
        String status = StatusConstant.ORDER_STATUS_EFFECT;

        // 通过id设置订单为已付款
        roomOrderPOMapper.updatePayStatusAndStatus(cashierPayPO.getRoomOrderId(), payStatus, status);

        // 设置押金状态为未退 0--未退 1--已退
        String depositStatus = StatusConstant.DEPOSIT_STATUS_NOT_FINISH;
        cashierPayPO.setStatus(depositStatus);

        // 付款之后设置房间id为1--正在使用
        Integer roomStatus = StatusConstant.ROOM_STATUS_USING;
        roomMapper.updateRoomStatusByOrderId(cashierPayPO.getRoomOrderId(), roomStatus);

        // 存入数据库
        depositMapper.insertCashierPay(cashierPayPO);
    }

    /**
     * 拿到押金管理页面
     * @param pageIndex 页码
     * @param pageSize 页面最大信息量
     * @return 押金管理封装信息类
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<DepositAndOrderVO> getDepositAndOrderVO(Integer pageIndex, Integer pageSize, String status) {

        // 开启分页
        PageHelper.startPage(pageIndex, pageSize);

        // 拿到押金订单封装类
        List<DepositAndOrderVO> depositAndOrderVOList = depositMapper.selectDepositAndOrderVOByStatus(status);

        // 放入分页
        PageInfo<DepositAndOrderVO> pageInfo = new PageInfo<>(depositAndOrderVOList);

        return pageInfo;
    }

    /**
     * 根据押金Id退还押金
     * @param map 押金id的map
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void giveBackDeposit(Map<String, Long> map) {

        // 从map中取出depositId
        Long depositId = map.get("depositId");

        // 判断
        if (depositId == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        depositMapper.updateDepositStatusById(depositId);
    }
}
