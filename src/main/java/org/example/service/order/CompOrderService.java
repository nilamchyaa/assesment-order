package org.example.service.order;

import jakarta.transaction.Transactional;
import org.example.constants.WorkflowStatus;
import org.example.dto.*;
import org.example.entity.Inventory;
import org.example.entity.Item;
import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.service.inventory.CompInventoryService;
import org.example.service.inventory.InventoryService;
import org.example.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CompOrderService {
    @Autowired private ItemService itemService;
    @Autowired private CompInventoryService compInventoryService;
    @Autowired private OrderService orderService;

    public GeneralResponse<List<OrderDto>> getAllOrders(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Order> pageResult= orderService.getAllOrders(pageable);

        List<OrderDto> dtoList = pageResult.getContent().stream()
                .map(order -> {
                    List<OrderDetailDto> detailDtos = order.getDetails().stream()
                            .map(detail -> {
                                Item item = detail.getItem();
                                ItemDto itemDto = new ItemDto(item.getId(), item.getName(), item.getPrice());
                                return new OrderDetailDto(
                                        detail.getId(),
                                        order.getOrder_no(),
                                        itemDto,
                                        detail.getQty(),
                                        detail.getPrice()
                                );
                            })
                            .toList();

                    return new OrderDto(
                            order.getId(),
                            order.getOrder_no(),
                            order.getTotalPrice(),
                            detailDtos
                    );
                })
                .toList();


        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully retrieved all order data.",
                pageResult.getTotalElements(),
                (long) pageResult.getNumberOfElements(),
                (long) page,
                (long) pageResult.getTotalPages(),
                dtoList);
    }

    public GeneralResponse<OrderDto> getById(Long id){
        Order order = orderService.getOrderByIdOrThrow(id);

        List<OrderDetailDto> detailDtos = order.getDetails().stream()
                .map(detail -> {
                    Item item = detail.getItem();
                    ItemDto itemDto = new ItemDto(item.getId(), item.getName(), item.getPrice());
                    return new OrderDetailDto(
                            detail.getId(),
                            order.getOrder_no(),
                            itemDto,
                            detail.getQty(),
                            detail.getPrice()
                    );
                }).toList();

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderNo(order.getOrder_no());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setDetail(detailDtos);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully retrieved order data.",
                orderDto);
    }

    @Transactional
    public GeneralResponse saveOrder(NewOrderRequest request){
        Order order = new Order();
        int i = 1;
        int qty;
        Long currentItemId = Long.parseLong("0");
        int currentQty = 0;
        order.setOrder_no(request.getOrder_no());

        Map<Long, OrderDetail> detailMap = new LinkedHashMap<>();
        double totalPrice = 0.0;
        request.getDetails().sort(Comparator.comparing(d -> Long.parseLong(d.getItem_id())));

        for (NewOrderDetailRequest detailReq : request.getDetails()) {
            Long itemId = Long.parseLong(detailReq.getItem_id());
            qty = detailReq.getQty();

            if (i == 0) {
                currentItemId = itemId;
                currentQty = qty;
            } else {
                if (!Objects.equals(currentItemId, itemId)) {
                    currentItemId = itemId;
                    currentQty = qty;
                } else {
                    currentQty += qty;
                }
            }

            Item item = itemService.getItemByIdOrThrow(itemId);
            Long remainStock = compInventoryService.getTotalRemain(itemId);

            if (currentQty > remainStock.longValue()) {
                throw new RuntimeException("Stok tidak cukup. Stock tersisa : " + remainStock);
            }

            OrderDetail detail = detailMap.getOrDefault(itemId, new OrderDetail());
            detail.setOrder(order);
            detail.setItem(item);
            detail.setQty(qty);
            detail.setPrice(item.getPrice());

            detailMap.put(itemId, detail);

            NewInventoryRequest newInventoryRequest = new NewInventoryRequest(item.getId().toString(), qty, "W");
            compInventoryService.createNewInventory(newInventoryRequest);
            totalPrice += item.getPrice() * qty;
        }

        List<OrderDetail> orderDetails = new ArrayList<>(detailMap.values());
        order.setDetails(orderDetails);
        order.setTotalPrice(totalPrice);

        orderService.saveOrder(order);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully added new order"
        );
    }


    @Transactional
    public GeneralResponse updateOrder(Long orderId, UpdateOrderRequest request) {
        Item item;
        Integer qty;
        Order order = orderService.getOrderByIdOrThrow(orderId);

        order.setOrder_no(request.getOrder_no());

        Map<Long, OrderDetail> existingDetails = order.getDetails().stream()
                .collect(Collectors.toMap(OrderDetail::getId, Function.identity()));

        List<OrderDetail> updatedDetails = new ArrayList<>();
        double totalPrice = 0.0;

        for (UpdateOrderDetailRequest detailReq : request.getDetails()) {
            OrderDetail detail;
            qty = detailReq.getQty();

            item = itemService.getItemByIdOrThrow(detailReq.getItem_id());
            if (detailReq.getId() != null && existingDetails.containsKey(detailReq.getId())) {
                detail = existingDetails.get(detailReq.getId());
                detail.setItem(item);
                detail.setQty(qty);
                detail.setPrice(item.getPrice());
            } else {
                detail = new OrderDetail();
                detail.setOrder(order);
                detail.setItem(item);
                detail.setQty(qty);
                detail.setPrice(item.getPrice());
            }

            totalPrice += detail.getQty() * detail.getPrice();
            updatedDetails.add(detail);
        }

        order.getDetails().clear();
        order.getDetails().addAll(updatedDetails);

        order.setTotalPrice(totalPrice);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully updated new order"
        );
    }

    @Transactional
    public GeneralResponse deleteOrder(Long id) {
        Order order = orderService.getOrderByIdOrThrow(id);

        orderService.deleteOrder(order);

        return new GeneralResponse<>(WorkflowStatus.SUCCESS,
                "Successfully to remove order"
        );
    }

}
