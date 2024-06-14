package com.walgula.hotel.service;

import com.walgula.hotel.model.RoomsOptimizationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsOptimizationService {

    public RoomsOptimizationResponse optimize(int availablePremiumRooms,
                                              int availableEconomyRooms,
                                              List<Double> guestsPayments) {
        return new RoomsOptimizationResponse(3,30.0, 4, 40.0);
    }

}