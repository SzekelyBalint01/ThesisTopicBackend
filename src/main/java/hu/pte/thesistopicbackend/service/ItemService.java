package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.NewItemDto;
import hu.pte.thesistopicbackend.model.*;
import hu.pte.thesistopicbackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {


    private final ItemRepository itemRepository;
    private final ItemConnectToGroupRepository itemConnectToGroupRepository;
    private final ItemConnectToUserRepository itemConnectToUserRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository,
                       ItemConnectToGroupRepository itemConnectToGroupRepository,
                       ItemConnectToUserRepository itemConnectToUserRepository,
                       GroupRepository groupRepository,
                       UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.itemConnectToGroupRepository = itemConnectToGroupRepository;
        this.itemConnectToUserRepository = itemConnectToUserRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Item createItem(NewItemDto newItemDto) {

        Item newItem = Item.builder()
                .name(newItemDto.getName())
                .price(newItemDto.getPrice())
                .currency(newItemDto.getCurrency())
                .mapUrl(newItemDto.getMapUrl())
                .description(newItemDto.getDescription())
                .paid(newItemDto.getPaidBy())
                .build();

        itemRepository.save(newItem);

        ItemConnectToGroupKey itemConnectToGroupKey = new ItemConnectToGroupKey((long) newItemDto.getGroupId(),newItem.getId());

        ItemConnectToGroup itemConnectToGroup = ItemConnectToGroup.builder()
                .id(itemConnectToGroupKey)
                .item(newItem)
                .group(groupRepository.findById((long) newItemDto.getGroupId()).orElseThrow(EntityNotFoundException::new))
                .build();

        itemConnectToGroupRepository.save(itemConnectToGroup);


        for (int i = 0; i< newItemDto.getUsers().size(); i++) {

            ItemConnectToUserKey itemConnectToUserKey = new ItemConnectToUserKey((long)newItemDto.getUsers().get(i),newItem.getId());
            ItemConnectToUser itemConnectToUser = ItemConnectToUser.builder()
                    .id(itemConnectToUserKey)
                    .item(newItem)
                    .user(userRepository.findById((long)newItemDto.getUsers().get(i)).orElseThrow(EntityNotFoundException::new))
                    .build();

            itemConnectToUserRepository.save(itemConnectToUser);
        }

        return newItem;
    }

    public List<Item> getAllItemByGroupId(Long groupId) {

        List<Item> items = itemRepository.findByGroupId(groupId);

        return items;
    }

    public Item getItemById(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new EntityNotFoundException());
        return item;
    }

    @Transactional
    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
