package com.butterchicken.UberAssistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RestaurantDto {

    private String name;

    private Double rating;

    private String openTime;

    private String closingTime;

    private Integer costForOne;

    private String imageUrl;

    private Integer resTables;

    private List<ReservationDto> reservationSlots;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationDto{

        private LocalDateTime timeSlot;

        private Integer availableCount;

    }

}
