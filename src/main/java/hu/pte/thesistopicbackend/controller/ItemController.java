package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.ItemDto;
import hu.pte.thesistopicbackend.dto.NewItemDto;
import hu.pte.thesistopicbackend.repository.ItemConnectToGroupRepository;
import hu.pte.thesistopicbackend.repository.ItemConnectToUserRepository;
import hu.pte.thesistopicbackend.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;
    private final ItemConnectToGroupRepository itemConnectToGroupRepository;
    private final ItemConnectToUserRepository itemConnectToUserRepository;

    public ItemController(ItemService itemService,
                          ItemConnectToGroupRepository itemConnectToGroupRepository,
                          ItemConnectToUserRepository itemConnectToUserRepository) {
        this.itemService = itemService;
        this.itemConnectToGroupRepository = itemConnectToGroupRepository;
        this.itemConnectToUserRepository = itemConnectToUserRepository;
    }

    @PostMapping("/createItem")
    public ResponseEntity<ItemDto> createNewItem(@RequestBody NewItemDto newItemDto) {

        ItemDto newItem = itemService.createItem(newItemDto);

        return new ResponseEntity<>(newItem, HttpStatus.OK);
    }



    @GetMapping("/getAllItem")
    public ResponseEntity<List<ItemDto>> getAllItem(@RequestParam("groupId") int groupId) {

        List<ItemDto> items = itemService.getAllItemByGroupId((long) groupId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/getItem")
    public ResponseEntity<ItemDto> getItemById(@RequestParam("itemId") int itemId) {

        ItemDto items = itemService.getItemById(itemId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/deleteItem")
    public ResponseEntity<Void> deleteItem(@RequestParam("itemId") int itemId) {
        itemConnectToGroupRepository.deleteByItemId(itemId);
        itemConnectToUserRepository.deleteByItemId(itemId);
        itemService.deleteItemById((long) itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
