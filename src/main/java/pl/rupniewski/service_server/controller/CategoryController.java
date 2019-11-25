package pl.rupniewski.service_server.controller;

import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Category;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin
public class CategoryController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(CategoryController.class.getName());

    @PostMapping(value = "/add")
    public Category addCategory(@RequestBody Category category) {
        LOGGER.info("adding new category: " + category);
        return categoryRepository.save(category);
    }

    @GetMapping(value = "/")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Category getCategoryById(@RequestParam Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->new ResourceNotFundException("Category", "id", id));
    }
}
