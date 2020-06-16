package pl.rupniewski.service_server.controller;

import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.BaseModel;
import pl.rupniewski.service_server.model.Chat;
import pl.rupniewski.service_server.model.Message;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/chat")
@CrossOrigin
public class ChatController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(BaseController.class.getName());

    @GetMapping(value = "/")
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @PostMapping(value = "/")
    public Chat getChatOrCreateOne(@RequestParam("user1Id") Long user1Id, @RequestParam("user2Id") Long user2Id, @RequestParam("shopName") String shopName) {
        Chat chat = chatRepository.findByUser1IdAndUser2Id(user1Id, user2Id).orElse(null);
        if (chat == null) {
            chat = new Chat();
            chat.setUser1Id(user1Id);
            chat.setUser2Id(user2Id);
            chat.setShopName(shopName);
            chatRepository.save(chat);
        }
        return chat;
    }
    @GetMapping(value = "/{user1Id}")
    public List<Chat> getAllChatsForUser(@PathVariable Long user1Id) {
        LOGGER.info(String.format("Getting List of chats for user1Id: %d", user1Id));
        return chatRepository.findByUser1IdOrUser2Id(user1Id, user1Id).orElse(Collections.emptyList());
    }
    @PostMapping(value = "/{id}/addMessage")
    public Chat addMessage(@PathVariable Long id, @RequestBody Message message) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("Chat", "id", id));
        chat.getMessages().add(message);
        System.out.println(message);
        return chatRepository.save(chat);
    }
}
