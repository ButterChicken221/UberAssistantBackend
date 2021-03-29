package com.butterchicken.UberAssistant.service;

import com.butterchicken.UberAssistant.dto.ReservationRequestDto;
import com.butterchicken.UberAssistant.entity.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation makeReservation(ReservationRequestDto reservationRequest);

    List<Reservation> getUpcomingRestaurantReservations(Integer restaurantId);
}
