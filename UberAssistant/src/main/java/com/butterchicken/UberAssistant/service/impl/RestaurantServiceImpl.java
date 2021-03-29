package com.butterchicken.UberAssistant.service.impl;

import com.butterchicken.UberAssistant.dto.RestaurantDto;
import com.butterchicken.UberAssistant.dto.RideDto;
import com.butterchicken.UberAssistant.entity.Reservation;
import com.butterchicken.UberAssistant.entity.Restaurant;
import com.butterchicken.UberAssistant.repo.RestaurantRepo;
import com.butterchicken.UberAssistant.service.ReservationService;
import com.butterchicken.UberAssistant.service.RestaurantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private ReservationService reservationService;

    @Override
    public List<RestaurantDto> getAllRestaurants(Integer page, Integer size) {
        Page<Restaurant> restaurantPage = restaurantRepo.findAll(PageRequest.of(page, size));
        if(restaurantPage.hasContent()){
            return restaurantPage.getContent().stream().map(e -> getRestaurantDetails(e)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Restaurant> findById(Integer restaurantId) {
        return restaurantRepo.findById(restaurantId);
    }

    private RestaurantDto getRestaurantDetails(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurant, restaurantDto);
        restaurantDto.setLocation(new RideDto.LatLong());
        restaurantDto.getLocation().setLatitude(restaurant.getLatitude());
        restaurantDto.getLocation().setLongitude(restaurant.getLongitude());
        List<Reservation> reservations = reservationService.getUpcomingRestaurantReservations(restaurant.getId());
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime = currentTime.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        Map<LocalDateTime, Integer> reservedSlots = new HashMap<>();
        reservations.stream().forEach(e -> {
            Integer reservedCount = (reservedSlots.getOrDefault(e.getReservationTime(), 0)) + 1;
            reservedSlots.put(e.getReservationTime(), reservedCount);
        });
        restaurantDto.setReservationSlots(new ArrayList<RestaurantDto.ReservationDto>());
        for(int i = 0 ; i < 24 ; i++){
            restaurantDto.getReservationSlots().add(new RestaurantDto.ReservationDto(currentTime.plusHours(i),
                    Math.max(0, restaurant.getResTables() - reservedSlots.getOrDefault(currentTime.plusHours(i), 0))));
        }
        return restaurantDto;
    }
}
