package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.OrderDetailDTO;

public interface OrderDetailService {

    OrderDetailDTO getOrderDetailById(Long id);

    List<OrderDetailDTO> getAllOrderDetails();

    OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO);

    OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO, Long id);

    void deleteOrderDetail(Long id);

  

}
