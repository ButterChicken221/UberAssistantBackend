package com.butterchicken.UberAssistant.repo;

import com.butterchicken.UberAssistant.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByRestaurantIdAndReservationTimeGreaterThan(Integer restaurantId, LocalDateTime date);

    Reservation findFirstByUserIdAndReservationTimeGreaterThanOrderByReservationTimeAsc(Integer userId, LocalDateTime now);
}
