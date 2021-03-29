package com.butterchicken.UberAssistant.service.impl;

import com.butterchicken.UberAssistant.dto.RideDto;
import com.butterchicken.UberAssistant.entity.Reservation;
import com.butterchicken.UberAssistant.entity.Restaurant;
import com.butterchicken.UberAssistant.entity.Ride;
import com.butterchicken.UberAssistant.enums.CabType;
import com.butterchicken.UberAssistant.repo.RideRepo;
import com.butterchicken.UberAssistant.service.ReservationService;
import com.butterchicken.UberAssistant.service.RestaurantService;
import com.butterchicken.UberAssistant.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RideServiceImpl implements RideService {

    @Autowired
    private RideRepo rideRepo;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public List<RideDto> getRideHistory(Integer userId, Integer page, Integer size) {
        Sort sortBy = Sort.by("id").descending();
        List<Ride> rideHistory = rideRepo.findByUserId(userId, PageRequest.of(page, size, sortBy));
        return rideHistory.stream().map(e -> getRideResponseDto(e)).collect(Collectors.toList());
    }

    private RideDto getRideResponseDto(Ride ride) {
        RideDto rideDto = RideDto.builder().userId(ride.getUserId())
                    .source(new RideDto.LatLong(ride.getSourceLat(), ride.getSourceLong()))
                        .destination(new RideDto.LatLong(ride.getDestLat(), ride.getDestLong()))
                        .fare(ride.getFare()).startTime(ride.getStartTime()).endTime(ride.getEndTime())
                        .cabTypeCode(ride.getCabType().getCabTypeCode()).build();
        return rideDto;
    }

    @Override
    public RideDto getSuggestedRide(Integer userId, Double currLat, Double currLong) {
        Reservation reservation = reservationService.findNextReservationForUser(userId);
        if(reservation != null && reservation.getReservationTime().isAfter(LocalDateTime.now()) && reservation.getReservationTime().isBefore(LocalDateTime.now().plusHours(6))){
            Optional<Restaurant> restaurant = restaurantService.findById(reservation.getRestaurantId());
            if(restaurant.isPresent()){
                Integer fare = Math.abs(new Random().nextInt())%400;
                fare = Math.max(fare, 400 - fare);
                return RideDto.builder().source(new RideDto.LatLong(currLat, currLong)).destination(new RideDto.LatLong(restaurant.get().getLatitude(), restaurant.get().getLongitude())).startTime(LocalDateTime.now().plusMinutes(5)).fare((double)fare).cabTypeCode(CabType.GO.getCabTypeCode()).build();
            }
        }
        return RideDto.builder().build();
    }

}
