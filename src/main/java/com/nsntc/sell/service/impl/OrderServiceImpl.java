package com.nsntc.sell.service.impl;

import com.nsntc.sell.converter.OrderMaster2OrderDTOConverter;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.enums.OrderStatusEnum;
import com.nsntc.sell.enums.PayStatusEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.pojo.dto.CartDTO;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.pojo.po.OrderDetail;
import com.nsntc.sell.pojo.po.OrderMaster;
import com.nsntc.sell.pojo.po.ProductInfo;
import com.nsntc.sell.repository.OrderDetailRepository;
import com.nsntc.sell.repository.OrderMasterRepository;
import com.nsntc.sell.service.IOrderService;
import com.nsntc.sell.service.IProductService;
import com.nsntc.sell.service.pay.wechat.IPayService;
import com.nsntc.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Name: OrderServiceImpl
 * Package: com.nsntc.sell.service.impl
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/3 下午3:46
 * Version: 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private IPayService payService;
    @Autowired
    private IPushMessageService pushMessageService;
    @Autowired
    private IWebSocket webSocket;

    /**
     * Method Name: create
     * Description: 创建订单
     * Create DateTime: 2017/12/6 上午12:07
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        /** 总价 */
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        /** 查询商品（数量, 价格）*/
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            ProductInfo productInfo =  this.productService.findOne(orderDetail.getProductId());
            if (null == productInfo) {
                throw new ExceptionCustom(HttpResultEnum.PRODUCT_NOT_EXIST);
            }

            /** 订单总价 */
            orderAmount =
                    /** BigDecimal乘法 */
                    productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    /** BigDecimal加法 */
                    .add(orderAmount);

            /** 订单详情入库 */
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            this.orderDetailRepository.save(orderDetail);
        }

        /** 写入订单数据库(orderMaster和orderDetail) */
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        this.orderMasterRepository.save(orderMaster);

        /** 扣库存 lambda表达式 */
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        this.productService.decreaseStock(cartDTOList);

        /** 发送websocket消息 */
        this.webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    /**
     * Method Name: findOne
     * Description: 查询单个订单
     * Create DateTime: 2017/12/6 上午12:07
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = this.orderMasterRepository.findOne(orderId);
        if (null == orderMaster) {
            throw new ExceptionCustom(HttpResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = this.orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new ExceptionCustom(HttpResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * Method Name: findList
     * Description: 查询订单列表
     * Create DateTime: 2017/12/6 上午12:07
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = this.orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        /** PageImpl(List<T> content, Pageable pageable, long total) */
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    /**
     * Method Name: cancel
     * Description: 取消订单、退款
     * Create DateTime: 2017/12/6 上午12:08
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        /** 判断订单状态 */
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new ExceptionCustom(HttpResultEnum.ORDER_STATUS_ERROR);
        }

        /** 修改订单状态 */
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = this.orderMasterRepository.save(orderMaster);
        if (null == updateResult) {
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new ExceptionCustom(HttpResultEnum.ORDER_UPDATE_FAIL);
        }

        /** 返回库存 */
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new ExceptionCustom(HttpResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        this.productService.increaseStock(cartDTOList);

        /** 如果已支付, 需要退款 */
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            this.payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     * Method Name: finish
     * Description: 完结订单
     * Create DateTime: 2017/12/6 上午12:08
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO finish(OrderDTO orderDTO) {
        /** 判断订单状态 */
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new ExceptionCustom(HttpResultEnum.ORDER_STATUS_ERROR);
        }

        /** 修改订单状态 */
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = this.orderMasterRepository.save(orderMaster);
        if (null == updateResult) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new ExceptionCustom(HttpResultEnum.ORDER_UPDATE_FAIL);
        }

        /** 推送微信模版消息 */
        this.pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }

    /**
     * Method Name: paid
     * Description: 支付订单
     * Create DateTime: 2017/12/6 上午12:09
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO paid(OrderDTO orderDTO) {
        /** 判断订单状态 */
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new ExceptionCustom(HttpResultEnum.ORDER_STATUS_ERROR);
        }

        /** 判断支付状态 */
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new ExceptionCustom(HttpResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        /** 修改支付状态 */
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = this.orderMasterRepository.save(orderMaster);
        if (null == updateResult) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new ExceptionCustom(HttpResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * Method Name: findList
     * Description: 查询订单列表(分页)
     * Create DateTime: 2017/12/6 上午12:09
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
