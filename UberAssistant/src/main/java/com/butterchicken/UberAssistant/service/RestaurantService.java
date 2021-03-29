package com.butterchicken.UberAssistant.service;

import com.butterchicken.UberAssistant.dto.RestaurantDto;
import com.butterchicken.UberAssistant.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    List<RestaurantDto> getAllRestaurants(Integer page, Integer size);

    Optional<Restaurant> findById(Integer restaurantId);
}
