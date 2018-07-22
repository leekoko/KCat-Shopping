package com.taotao.order.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.TCKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("{ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("{ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	
	
	
	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
		//向订单中插入数据
		//获得订单号
		String str = jedisClient.get(ORDER_GEN_KEY);
		if(StringUtils.isBlank(str)){
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		long orderId = jedisClient.incr(ORDER_INIT_ID);
		//补全pojo
		order.setOrderId(orderId + "");
		//状态：1未付款 2已付款 3为发挥 4已发货 5交易成功 6交易失败
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		//0未评价 1已评价
		order.setBuyerRate(0);
		//向订单表插入数据
		orderMapper.insert(order);
		//插入订单明细
		for(TbOrderItem tbOrderItem : itemList){
			//补全订单明细
			//获取订单明细id
			long orderDetailId = jedisClient.incr(ORDER_GEN_KEY);
			tbOrderItem.setId(orderDetailId + "");
			tbOrderItem.setOrderId(orderId + "");
			//向订单明细插入记录
			orderItemMapper.insert(tbOrderItem);
		}
		//插入物流表
		//补全物流表
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		return TaotaoResult.ok();
	}

}
