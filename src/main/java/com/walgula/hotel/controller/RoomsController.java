package com.walgula.hotel.controller;
import com.walgula.hotel.dto.RoomsRequestDto;

import com.walgula.hotel.model.RoomsOptimizationResponse;
import com.walgula.hotel.service.RoomsOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class RoomsController {

    @Autowired
    private RoomsOptimizationService roomsOptimizationService;


    @PostMapping("/optimize")
    public RoomsOptimizationResponse optimize(@RequestBody RoomsRequestDto request) {
        if (request.getAvailablePremiumRooms() <= 0 || request.getAvailableEconomyRooms() <= 0) {
            throw new IllegalArgumentException("Room numbers must be greater than zero.");
        }
        if (request.getGuestsPayments().isEmpty()) {
            throw new IllegalArgumentException("Payments must not be empty.");
        }
        if (request.getGuestsPayments().stream().anyMatch(x->x<=0)){
            throw new IllegalArgumentException("Payments must be greater than zero.");
        }
        return roomsOptimizationService.optimize(request.getAvailablePremiumRooms(),
                request.getAvailableEconomyRooms(), request.getGuestsPayments());
    }

}
