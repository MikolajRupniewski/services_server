package pl.rupniewski.service_server.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.*;
import sun.rmi.runtime.Log;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/shops")

public class ShopController extends BaseController{

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @GetMapping("")
    public List<Shop> getAllShops() {
        LOGGER.info("getting all shops");
//        Shop shop = new Shop();
//        shop.setCategories(Arrays.asList(new Category("Fryzjer"), new Category("Manicure")));
//        shop.setGeoLocation(new GeoLocation(12.123123f,12.12312332f));
//        shop.setPictures(Arrays.asList("http://localhost:8080/downloadFile/C_578190.jpg","http://localhost:8080/downloadFile/indeks.jpg"));
//        shop.setServicePlace(ServicePlace.AT_SHOP);
//        shop.setMaxDistance(25.0);
//        shop.setUsers(usersRepository.getOne(12L));
//        shop.setServices(Arrays.asList(
//                new Service("Ciecie meskie", Duration.ofMinutes(30), 25.0),
//                new Service("Ciecie damskie", Duration.ofMinutes(60), 50.0),
//                new Service("Manicure", Duration.ofMinutes(60), 50.0)
//        ));
//        shopRepository.save(shop);
        return shopRepository.findAll();
    }

    @GetMapping("/{id}")
    public Shop getShopById(@PathVariable Long id) {
        LOGGER.info("getting shop for id: " + id);
        return shopRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("Shop", "id", id));
    }
    @GetMapping("/findByServiceName")
    public List<Shop> getShopsByServiceName(@RequestParam String name) {
        LOGGER.info("getting shop for name: " + name);
        return shopRepository.findByServices_nameContaining(name).orElseThrow(() -> new ResourceNotFundException("Shop", "Service name", name));
    }

    @GetMapping("/findByUserUsername")
    public List<Shop> getShopsByUsersUsername(@RequestParam String username) {
        LOGGER.info("getting shops for User name: " + username);
        return null;
    }

    @PostMapping("")
    public Shop addShop(@RequestParam Long id, @RequestBody Shop shop, HttpServletResponse response) {
        LOGGER.info("adding new shop: " + shop);
        Category shopCategory = categoryRepository.findByName(shop.getCategory().getName());
        shop.setCategory(shopCategory);

        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        users.setShop(shop);

        for (Service s : shop.getServices()) {
            s.setDurationFromString();
        }
        usersRepository.save(users);
        if(users.getShop() != null){
            LOGGER.info("shop added");
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        else{
            LOGGER.warning("shop could not be added");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return shop;
    }

    @PutMapping("/{id}")
    public Shop addOrder(@RequestBody Order order, @PathVariable Long id, @RequestParam long timestamp) {
        Date date = new Date();
        date.setTime(timestamp);
        date.setHours(date.getHours()+1);
        order.setDate(date);
        LOGGER.info("Time: " + date);
        LOGGER.info("updating shop for id: "+ id);
        Shop shopToUpdate = shopRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("Shop", "id", id));
        shopToUpdate.getOrders().add(order);
        System.out.println(order.getDate());
        System.out.println("User: " + order.getUsers());
        return shopRepository.save(shopToUpdate);
    }

    @DeleteMapping("/{id}")
    public Shop deleteShop(@PathVariable Long id) {
        LOGGER.info("deleting shop for id: " + id);
        return shopRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("Shop", "id", id));

    }
    @GetMapping("/findByUserId/{id}")
    public Shop findByUserId(@PathVariable Long id) {
        return null;
    }
    @PutMapping("/addOrderFeedback/{id}")
    public Shop addOrderFeedback(@PathVariable Long id, @RequestBody Order order) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("Shop", "id", id));
        for (Order order1 : shop.getOrders()) {
            if (order1.getId().equals(order.getId())) {
                order1.setComment(order.getComment());
                order1.setRating(order.getRating());
                System.out.println("Comment: " + order1.getComment());
                System.out.println("Rating: " + order1.getRating());
            }
        }

        return shopRepository.save(shop);
    }
}
