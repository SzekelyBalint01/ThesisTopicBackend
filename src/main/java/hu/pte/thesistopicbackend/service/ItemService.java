package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.NewItemDto;
import hu.pte.thesistopicbackend.model.Item;
import hu.pte.thesistopicbackend.model.ItemConnectToGroup;
import hu.pte.thesistopicbackend.model.ItemConnectToUser;
import hu.pte.thesistopicbackend.repository.ItemConnectToGroupRepository;
import hu.pte.thesistopicbackend.repository.ItemConnectToUserRepository;
import hu.pte.thesistopicbackend.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {


    private final ItemRepository itemRepository;
    private final ItemConnectToGroupRepository itemConnectToGroupRepository;
    private final ItemConnectToUserRepository itemConnectToUserRepository;

    public ItemService(ItemRepository itemRepository,
                       ItemConnectToGroupRepository itemConnectToGroupRepository,
                       ItemConnectToUserRepository itemConnectToUserRepository) {
        this.itemRepository = itemRepository;
        this.itemConnectToGroupRepository = itemConnectToGroupRepository;
        this.itemConnectToUserRepository = itemConnectToUserRepository;
    }

    public Item createItem(NewItemDto newItemDto) {

        Item newItem = Item.builder()
                .name(newItemDto.getName())
                .price(newItemDto.getPrice())
                .currency(newItemDto.getCurrency())
                .mapUrl(newItemDto.getMapUrl())
                .description(newItemDto.getDescription())
                .build();

        itemRepository.save(newItem);

        ItemConnectToGroup itemConnectToGroup = ItemConnectToGroup.builder()
                .itemId(newItem.getId())
                .groupId((long) newItemDto.getGroupId())
                .build();

        itemConnectToGroupRepository.save(itemConnectToGroup);

        for (int i = 0; i< newItemDto.getUsers().size(); i++) {
            ItemConnectToUser itemConnectToUser = ItemConnectToUser.builder()
                    .itemId(newItem.getId())
                    .userId(Long.valueOf(newItemDto.getUsers().get(i)))
                    .build();

            itemConnectToUserRepository.save(itemConnectToUser);
        }

        return newItem;
    }

    public List<Item> getAllItemByGroupId(Long groupId) {

        ArrayList<ItemConnectToGroup> itemConnectToGroupArrayList = itemConnectToGroupRepository.findItemByGroupId(groupId);
        ArrayList<Optional<Item>> items = new ArrayList<>();

        for (ItemConnectToGroup itemConnectToGroup: itemConnectToGroupArrayList) {
            items.add(itemRepository.findById(itemConnectToGroup.getItemId()));
        }

        if (items.isEmpty()){
            return null;
        }
        else{
            List<Item> result = items.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            return result;
        }

    }


    public Item getItemById(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new EntityNotFoundException());
        return item;
    }
}
