package pl.rupniewski.service_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.*;
import pl.rupniewski.service_server.repository.ShopRepository;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/shops")
public class ShopController extends BaseController{


    @GetMapping("")
    public List<Shop> getAllShops() {
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
        return shopRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("Shop", "id", id));
    }

    @PostMapping("")
    public Shop addShop(@RequestBody Shop shop, HttpServletResponse response) {
        /* TODO */
        return null;
    }

    @PutMapping("/{id}")
    public Shop updateShop(@RequestBody Shop shop, @PathVariable Long id) {
        /* TODO */
        return null;
    }

    @DeleteMapping("/{id}")
    public Shop deleteShop(@PathVariable Long id) {
        /* TODO */
        return null;
    }
}
