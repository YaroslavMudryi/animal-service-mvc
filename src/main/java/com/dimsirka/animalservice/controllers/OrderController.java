package com.dimsirka.animalservice.controllers;

import com.dimsirka.animalservice.dtoes.OrderDto;
import com.dimsirka.animalservice.entities.EmailMessageType;
import com.dimsirka.animalservice.entities.OrderStatus;
import com.dimsirka.animalservice.mapper.OrderDtoMapper;
import com.dimsirka.animalservice.services.EmailService;
import com.dimsirka.animalservice.services.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@PropertySource("classpath:application.yaml")
@Controller
@RequestMapping("api/orders")
public class OrderController {

    @Value("${mail.adminEmail}")
    private String adminEmail;
    private OrderService orderService;
    private OrderDtoMapper mapper;
    private EmailService emailService;

    public OrderController(OrderService orderService,
                           OrderDtoMapper mapper,
                           EmailService emailService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.emailService = emailService;
    }

    @GetMapping("/new/{id}")
    public String getCreateForm(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "order/create";
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute OrderDto orderDto, Model model){
        orderService.create(mapper.toEntity(orderDto));
        emailService.sendMessage(orderDto.getUserEmail(),"", EmailMessageType.USER_MESSAGE);
        emailService.sendMessage(adminEmail,"", EmailMessageType.ADMIN_MESSAGE);
        model.addAttribute("message", "The order has been pended!");
        return "redirect:/home";
    }

    @PostMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto update(@Validated @RequestBody OrderDto orderDto, @PathVariable Long orderId){
        orderDto.setId(orderId);
        return mapper.toDto(orderService.update(mapper.toEntity(orderDto)));
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getById(@PathVariable Long orderId){
        return mapper.toDto(orderService.getById(orderId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAll(){
        return mapper.toDtoList(orderService.getAll());
    }

    @PostMapping("/cancel/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void cancel(@PathVariable Long orderId) {
        orderService.cancelOrConfirm(orderId, OrderStatus.CANCELED);
    }

    @PostMapping("/confirm/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void confirm(@PathVariable Long orderId){
        orderService.cancelOrConfirm(orderId, OrderStatus.CONFIRMED);
    }
}
