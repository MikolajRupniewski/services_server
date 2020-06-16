package pl.rupniewski.service_server.controller;

import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Category;
import pl.rupniewski.service_server.model.Order;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping(value = "/orders")
public class OrderController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @GetMapping(value = "")
    public List<Order> getAllOrders() {
        LOGGER.info("Receiving all orders");
        return orderRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Order getOneOrder(@PathVariable Long id) {
        LOGGER.info("Receiving order for id=" + id);
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
    }

    @PostMapping(value = "")
    public Order addOrder(@RequestBody Order order) {
        LOGGER.info("adding new order: " + order);
        return orderRepository.save(order);
    }

    @DeleteMapping(value = "/{id}")
    public Order removeComment(@PathVariable Long id) {
        LOGGER.info("Removing comment for order id: "+ id);
        Order order = orderRepository.getOne(id);
        order.setRating(null);
        order.setComment(null);
        orderRepository.save(order);
        return order;
    }
}
