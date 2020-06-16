package pl.rupniewski.service_server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rupniewski.service_server.model.*;
import pl.rupniewski.service_server.repository.UsersRepository;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/mock")
public class MockData extends BaseController {

    @GetMapping(value = "/mock-users")
    public String mockUsers() {
        List<Users> users = Arrays.asList(
                new Users("mikolajR", "karconko123", true, "mikolaj@gmail.com"),
                new Users("adamK", "karconko123", true,"adam@gmial.com"),
                new Users("marcinJ", "karconko123", true,"marcin@gmial.com"),
                new Users("annaZ", "karconko123", true, "anna@gmial.com"),
                new Users("kacperJ", "karconko123", true, "kacper@gmial.com"),
                new Users("kingaS", "karconko123", true, "kinga@gmial.com"),
                new Users("agnieszkaU", "karconko123", true, "agnieszka@gmial.com"),
                new Users("kamilA", "karconko123", true, "kamil@gmial.com"),
                new Users("marcelD", "karconko123", true, "marcel@gmial.com"),
                new Users("KubaJ", "karconko123", true, "kuba@gmial.com"),
                new Users("angelikaP", "karconko123", true, "angelika@gmial.com"),
                new Users("IwonaK", "karconko123", true, "iwona@gmial.com")
        );
        usersRepository.saveAll(users);
        List<Authorities> authorities = Arrays.asList(
                new Authorities("mikolajR","USER" ),
                new Authorities("adamK","USER" ),
                new Authorities("marcinJ","USER" ),
                new Authorities("annaZ","USER" ),
                new Authorities("kacperJ","USER" ),
                new Authorities("kingaS","USER" ),
                new Authorities("agnieszkaU","USER" ),
                new Authorities("kamilA","USER" ),
                new Authorities("marcelD","USER" ),
                new Authorities("KubaJ","USER" ),
                new Authorities("angelikaP","USER" ),
                new Authorities("IwonaK","ADMIN" )
        );
        authoritiesRepository.saveAll(authorities);
        return "Ok";
    }
    @GetMapping(value = "/mock-categories")
    public String mockCategories() {
        List<Category> categories = Arrays.asList(
                new Category("Hairdresser"),
                new Category("Plumber"),
                new Category("PC Repair"),
                new Category("Dentist"),
                new Category("Massage"),
                new Category("Vet"),
                new Category("Handyman")
        );
        categoryRepository.saveAll(categories);
        return "OK";
    }
    @GetMapping(value = "/mock-shops")
    public String mockShops() {

        Day day = new Day("10:00", "20:00");
        Employee employee = new Employee();
        employee.setMonday(day);
        employee.setTuesday(day);
        employee.setWednesday(day);
        employee.setThursday(day);
        employee.setFriday(day);
        employee.setSaturday(day);
        employee.setSunday(day);

        Shop shop = new Shop();
        shop.setName("Barber Shop");
        shop.setCategory(categoryRepository.findByName("Hairdresser"));
        shop.setMaxDistance(0.0);
        shop.setServicePlace(ServicePlace.AT_SHOP);
        shop.setPictures(Collections.singletonList("https://thumbs.img-sprzedajemy.pl/1000x901c/e2/e5/31/do-sprzedania-barber-shop-w-centrum-krakowa-508863748.jpg"));
        shop.setGeoLocation(new GeoLocation(64.123f, 77.123f));
        shop.setEmployees(Collections.singletonList(employee));
        shop.setServices(Arrays.asList(
                new Service("Man's haircut", Duration.ofMinutes(30), 30.0),
                new Service("Women's haircut", Duration.ofMinutes(60), 70.0),
                new Service("Bread's haircut", Duration.ofMinutes(20), 20.0),
                new Service("Color change", Duration.ofMinutes(120), 230.0)
        ));

        Shop shop1 = new Shop();
        shop1.setName("Beauty Place");
        shop1.setCategory(categoryRepository.findByName("Hairdresser"));
        shop1.setMaxDistance(0.0);
        shop1.setServicePlace(ServicePlace.AT_SHOP);
        shop1.setGeoLocation(new GeoLocation(63.123f, 74.123f));
        shop1.setEmployees(Collections.singletonList(employee));
        shop1.setPictures(Collections.singletonList("https://d375139ucebi94.cloudfront.net/pl/36814/186a0b1342de43a78a53747a9c732ad0-Beauty-Studio-KissKeratin-biz-photo.PNG"));
        shop1.setServices(Arrays.asList(
                new Service("Manicure", Duration.ofMinutes(30), 30.0),
                new Service("Pedicure", Duration.ofMinutes(60), 70.0)
        ));

        Shop shop2 = new Shop();
        shop2.setName("Adam handyman");
        shop2.setMaxDistance(20.0);
        shop2.setCategory(categoryRepository.findByName("Handyman"));
        shop2.setServicePlace(ServicePlace.AT_CUSTOMER);
        shop2.setGeoLocation(new GeoLocation(62.123f, 71.123f));
        shop2.setEmployees(Collections.singletonList(employee));
        shop2.setPictures(Collections.singletonList("https://media.istockphoto.com/vectors/handyman-holding-tools-vector-id636578758"));
        shop2.setServices(Arrays.asList(
                new Service("Repair kitchen", Duration.ofMinutes(30), 30.0),
                new Service("Repair bathroom", Duration.ofMinutes(60), 70.0),
                new Service("Repair bedroom", Duration.ofMinutes(100), 90.0)
        ));

        Shop shop3 = new Shop();
        shop3.setName("Marek Vet");
        shop3.setCategory(categoryRepository.findByName("Vet"));
        shop3.setMaxDistance(0.0);
        shop3.setServicePlace(ServicePlace.AT_SHOP);
        shop3.setGeoLocation(new GeoLocation(60.123f, 71.123f));
        shop3.setEmployees(Collections.singletonList(employee));
        shop3.setPictures(Collections.singletonList("https://lh3.googleusercontent.com/proxy/CR_YzaTHXKQVMaT9CPBN67aoZhvZUU2dEqwHLFNJgFFXqReMT9SkAnTfxaljDNbQGSNBt8ncuq9ytkZryU0dNxVkswXUxLvl4nqnPEdz1JY92Nqh6vmivQ"));
        shop3.setServices(Arrays.asList(
                new Service("Dog's repair", Duration.ofMinutes(30), 30.0),
                new Service("Cats's repair", Duration.ofMinutes(60), 70.0),
                new Service("Hamster's repair", Duration.ofMinutes(100), 90.0)
        ));

        Users one = usersRepository.getOne(1L);
        Users two = usersRepository.getOne(2L);
        Users three = usersRepository.getOne(3L);
        Users four = usersRepository.getOne(4L);
        one.setShop(shop);
        two.setShop(shop1);
        three.setShop(shop2);
        four.setShop(shop3);
        usersRepository.saveAll(Arrays.asList(one,two,three,four));
        return "Ok";
    }

    @GetMapping(value = "mock-chat")
    public String mockChat() {
        Chat chat = new Chat();
        chat.setUser2Id(2L);
        chat.setUser2Id(1L);
        chat.setShopName("Barber Shop");
        Users shop = usersRepository.getOne(1L);
        Users user = usersRepository.getOne(2L);
        Message message = new Message();
        message.setAuthor(user);
        message.setContent("Hi, I wanted to ask if I should wash my hair before the visit?");
        message.setDate(new Date());

        Message reply = new Message();
        reply.setAuthor(shop);
        reply.setContent("It's up to you, we can wash your hair when you come :)");
        reply.setDate(new Date());

        Message reply2 = new Message();
        reply2.setAuthor(user);
        reply2.setContent("Ok, thank you! See you there.");
        reply2.setDate(new Date());

        Message reply3 = new Message();
        reply3.setAuthor(shop);
        reply3.setContent("No problem, see you there.");
        reply3.setDate(new Date());

        chat.setMessages(Arrays.asList(message, reply, reply2, reply3));
        chatRepository.save(chat);
        return "Ok";
    }

}
