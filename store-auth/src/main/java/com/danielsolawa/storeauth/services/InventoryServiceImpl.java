package com.danielsolawa.storeauth.services;

import com.danielsolawa.storeauth.domain.User;
import com.danielsolawa.storeauth.dtos.InventoryDto;
import com.danielsolawa.storeauth.mappers.InventoryMapper;
import com.danielsolawa.storeauth.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    private final UserRepository userRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(UserRepository userRepository, InventoryMapper inventoryMapper) {
        this.userRepository = userRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Transactional
    @Override
    public InventoryDto createNewInventory(Long userId, InventoryDto inventoryDto) {
        User user = getUserById(userId);

        inventoryDto.setUser(user);

        user.setInventory(inventoryMapper.inventoryDtoToInventory(inventoryDto));

        User returnedUser = userRepository.save(user);

        return inventoryMapper.inventoryToInventoryDto(returnedUser.getInventory());
    }

    @Transactional
    @Override
    public InventoryDto updateInventory(Long userId, InventoryDto inventoryDto) {
        User user = getUserById(userId);

        user.setInventory(inventoryMapper.inventoryDtoToInventory(inventoryDto));

        User returnedUser = userRepository.save(user);

        return inventoryMapper.inventoryToInventoryDto(returnedUser.getInventory());
    }

    @Override
    public InventoryDto getInventoryByUserId(Long userId) {
        User user = getUserById(userId);

        return inventoryMapper.inventoryToInventoryDto(user.getInventory());
    }

    @Override
    public void deleteInventoryByUserId(Long userId) {

        User user = getUserById(userId);
        user.setInventory(null);

        userRepository.save(user);
    }


    private User getUserById(Long id){
        User user = userRepository.findOne(id);

        if(user == null){
            //todo
            // implement more fitting exception
            throw new RuntimeException();
        }

        return user;
    }
}
