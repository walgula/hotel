package com.walgula.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomsOptimizationResponse {
    private int premiumRoomsOccupation;
    private double premiumRoomsTotalRevenue;
    private int economyRoomsOccupation;
    private double economyRoomsTotalRevenue;
}
