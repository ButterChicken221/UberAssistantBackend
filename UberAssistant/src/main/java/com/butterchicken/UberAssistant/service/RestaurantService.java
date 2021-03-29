package com.butterchicken.UberAssistant.service;

import com.butterchicken.UberAssistant.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {

    List<RestaurantDto> getAllRestaurants(Integer page, Integer size);

}
