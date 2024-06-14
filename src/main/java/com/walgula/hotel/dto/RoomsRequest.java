package com.walgula.hotel.dto;

import lombok.Data;
import java.util.List;

@Data

public class RoomsRequest {
    private int availablePremiumRooms;
    private int availableEconomyRooms;
    private List<Double> guestsPayments;
}
