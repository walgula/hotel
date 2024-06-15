package com.walgula.hotel.service;

import com.walgula.hotel.model.RoomsOptimizationResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RoomsOptimizationService {

    public RoomsOptimizationResponse optimize(int availablePremiumRooms,
                                              int availableEconomyRooms,
                                              List<Double> guestsPayments) {

        // Sorting all guest payments from higher to lower
        List<Double> payments = guestsPayments.stream()
                .sorted(Collections.reverseOrder())
                .toList();

        double totalPremiumRevenue = 0;
        double totalEconomyRevenue = 0;
        int premiumRoomsOccupied = 0;
        int economyRoomsOccupied = 0;

        List<Double> paymentsFromEconomyGuests = new ArrayList<>();

        // Step 1: Allocate guests willing to pay >= 100 to premium rooms
        for (double payment : payments) {
            if (payment >= 100) {
                if (premiumRoomsOccupied < availablePremiumRooms) {
                    premiumRoomsOccupied++;
                    totalPremiumRevenue += payment;
                }
            } else {
                paymentsFromEconomyGuests.add(payment);
            }
        }

        // Step 2: Allocate remaining economy guests to economy rooms
        for (double payment : paymentsFromEconomyGuests) {
            if (economyRoomsOccupied < availableEconomyRooms) {
                economyRoomsOccupied++;
                totalEconomyRevenue += payment;
            } else if (premiumRoomsOccupied < availablePremiumRooms) {
                // Step 3: Upgrade highest paying economy guests to premium rooms if available
                premiumRoomsOccupied++;
                totalPremiumRevenue += payment;
            }
        }

        return new RoomsOptimizationResponse(premiumRoomsOccupied, totalPremiumRevenue,  economyRoomsOccupied, totalEconomyRevenue);
    }
}
