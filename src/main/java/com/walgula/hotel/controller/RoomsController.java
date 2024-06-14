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
        return roomsOptimizationService.optimize(request.getAvailablePremiumRooms(),
                request.getAvailableEconomyRooms(), request.getGuestsPayments());
    }

}
