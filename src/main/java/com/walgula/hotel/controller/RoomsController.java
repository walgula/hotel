package com.walgula.hotel.controller;

import com.walgula.hotel.dto.RoomsRequest;
import com.walgula.hotel.model.RoomsOptimizationResponse;
import com.walgula.hotel.service.RoomsOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {

    @Autowired
    private RoomsOptimizationService roomsOptimizationService;

    @PostMapping("/optimize")
    public RoomsOptimizationResponse optimize(@RequestBody RoomsRequest request) {
        validateInputData(request);
        return roomsOptimizationService.optimize(request.getAvailablePremiumRooms(),
                request.getAvailableEconomyRooms(), request.getGuestsPayments());
    }

    private void validateInputData(RoomsRequest request) {
        if (request.getAvailablePremiumRooms() <= 0 || request.getAvailableEconomyRooms() <= 0) {
            throw new IllegalArgumentException("Room numbers must be greater than zero.");
        }
        if (request.getGuestsPayments().isEmpty()) {
            throw new IllegalArgumentException("Payments must not be empty.");
        }
        if (request.getGuestsPayments().stream().anyMatch(x->x<=0)){
            throw new IllegalArgumentException("Payments must be greater than zero.");
        }
    }
}
