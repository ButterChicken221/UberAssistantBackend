package com.butterchicken.UberAssistant.service.impl;

import com.butterchicken.UberAssistant.dto.Note;
import com.butterchicken.UberAssistant.dto.ReservationRequestDto;
import com.butterchicken.UberAssistant.entity.Reservation;
import com.butterchicken.UberAssistant.repo.ReservationRepo;
import com.butterchicken.UberAssistant.service.FirebaseMessagingService;
import com.butterchicken.UberAssistant.service.ReservationService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Value("${default.firebase.token}")
    private String defaultFirebaseToken;

    @Override
    public Reservation makeReservation(ReservationRequestDto reservationRequest) {
        Reservation reservation = reservationRepo.save(new Reservation(reservationRequest.getUserId(),
                reservationRequest.getRestaurantId(), reservationRequest.getReservationTime()));
        if(reservation != null){
            Note note = new Note();
            note.setSubject("Table reserved successfully");
            note.setSubject("Navigate to Uber to book your ride!");
            note.setData(new HashMap<>());
            try {
                firebaseMessagingService.sendNotification(note, defaultFirebaseToken);
            }catch (FirebaseMessagingException e){
                e.printStackTrace();
            }
        }
        return reservation;
    }

    @Override
    public List<Reservation> getUpcomingRestaurantReservations(Integer restaurantId) {
        return reservationRepo.findByRestaurantIdAndReservationTimeGreaterThan(restaurantId, LocalDateTime.now());
    }

    @Override
    public Reservation findNextReservationForUser(Integer userId) {
        return reservationRepo.findFirstByUserIdAndReservationTimeGreaterThanOrderByReservationTimeAsc(userId, LocalDateTime.now());
    }
}
