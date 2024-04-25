package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.ItemDto;
import hu.pte.thesistopicbackend.dto.NewItemDto;
import hu.pte.thesistopicbackend.dto.UserDto;
import hu.pte.thesistopicbackend.model.*;
import hu.pte.thesistopicbackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public ItemDto createItem(NewItemDto newItemDto) {

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
                .item(itemRepository.findById(newItem.getId()).orElseThrow(EntityNotFoundException::new))
                .group(groupRepository.findById((long) newItemDto.getGroupId()).orElseThrow(EntityNotFoundException::new))
                .build();

        itemConnectToGroupRepository.save(itemConnectToGroup);

        for (Integer user: newItemDto.getUsers()) {
            ItemConnectToUserKey itemConnectToUserKey = new ItemConnectToUserKey((long)user, newItem.getId());
            ItemConnectToUser itemConnectToUser = ItemConnectToUser.builder()
                    .id(itemConnectToUserKey)
                    .user(userRepository.findById((long) user).orElseThrow(EntityNotFoundException::new))
                    .item(itemRepository.findById(newItem.getId()).orElseThrow(EntityNotFoundException::new))
                    .build();
            itemConnectToUserRepository.save(itemConnectToUser);
        }

        Item item = itemRepository.findById(newItem.getId()).orElseThrow(EntityNotFoundException::new);


        return mapItemToItemDTO(item);
    }

    public List<ItemDto> getAllItemByGroupId(Long groupId) {

        List<Item> items = itemRepository.findByGroupId(groupId);

        return items.stream().map(item -> ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .build()).toList();
    }


    public ItemDto mapItemToItemDTO(Item item) {

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .currency(item.getCurrency())
                .mapUrl(item.getMapUrl())
                .description(item.getDescription())
                .paid(item.getPaid())
                .build();
    }
    public ItemDto getItemById(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException());


        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .currency(item.getCurrency())
                .mapUrl(item.getMapUrl())
                .description(item.getDescription())
                .paid(item.getPaid())
                .users(item.getItemConnectToUsers().stream().map(user -> {
                    return UserDto.builder()
                            .id(user.getUser().getId())
                            .username(user.getUser().getUsername())
                            .build();
                }).toList())
                .build();
    }

    @Transactional
    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }


}
