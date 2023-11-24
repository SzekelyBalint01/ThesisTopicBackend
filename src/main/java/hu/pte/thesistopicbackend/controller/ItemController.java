package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.NewItemDto;
import hu.pte.thesistopicbackend.model.Item;
import hu.pte.thesistopicbackend.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/createItem")
    public ResponseEntity<Item> createNewItem(@RequestBody NewItemDto newItemDto){

            Item newItem = itemService.createItem(newItemDto);

            return new ResponseEntity<>(newItem, HttpStatus.OK);
    }


    @GetMapping("/getAllItem")
    public ResponseEntity<List<Item>> getAllItem(@RequestParam("groupId") int groupId){

          List<Item> items = itemService.getAllItemByGroupId((long) groupId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/getItem")
    public ResponseEntity<Item> getItemById(@RequestParam("itemId") int itemId){

        Item items = itemService.getItemById((long) itemId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
