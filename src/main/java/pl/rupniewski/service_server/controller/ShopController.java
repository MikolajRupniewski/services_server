package pl.rupniewski.service_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Shop;
import pl.rupniewski.service_server.repository.ShopRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/shops")
public class ShopController extends BaseController{


    @GetMapping("")
    public List<Shop> getAllShops() {
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
