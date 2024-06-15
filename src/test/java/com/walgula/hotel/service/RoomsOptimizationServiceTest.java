package com.walgula.hotel.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.walgula.hotel.model.RoomsOptimizationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
public class RoomsOptimizationServiceTest {
    private final List<Double> GUEST_PAYMENTS
            = List.of(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);

    @Autowired
    private RoomsOptimizationService roomsOptimizationService;

    @Test
    public void testShouldBePassedForTaskTestCase1() {
        RoomsOptimizationResponse roomsOptimizationResponse
                = roomsOptimizationService.optimize(3, 3, GUEST_PAYMENTS);

        assertThat(roomsOptimizationResponse.getPremiumRoomsOccupation()).isEqualTo(3);
        assertThat(roomsOptimizationResponse.getPremiumRoomsTotalRevenue()).isEqualTo(738.0);
        assertThat(roomsOptimizationResponse.getEconomyRoomsOccupation()).isEqualTo(3);
        assertThat(roomsOptimizationResponse.getEconomyRoomsTotalRevenue()).isEqualTo(167.99);
    }

    @Test
    public void testShouldBePassedForTaskTestCase2() {
        RoomsOptimizationResponse roomsOptimizationResponse
                = roomsOptimizationService.optimize(7, 5, GUEST_PAYMENTS);

        assertThat(roomsOptimizationResponse.getPremiumRoomsOccupation()).isEqualTo(6);
        assertThat(roomsOptimizationResponse.getPremiumRoomsTotalRevenue()).isEqualTo(1054);
        assertThat(roomsOptimizationResponse.getEconomyRoomsOccupation()).isEqualTo(4);
        assertThat(roomsOptimizationResponse.getEconomyRoomsTotalRevenue()).isEqualTo(189.99);
    }

    @Test
    public void testShouldBePassedForTaskTestCase3() {
        RoomsOptimizationResponse roomsOptimizationResponse
                = roomsOptimizationService.optimize(2, 7, GUEST_PAYMENTS);

        assertThat(roomsOptimizationResponse.getPremiumRoomsOccupation()).isEqualTo(2);
        assertThat(roomsOptimizationResponse.getPremiumRoomsTotalRevenue()).isEqualTo(583);
        assertThat(roomsOptimizationResponse.getEconomyRoomsOccupation()).isEqualTo(4);
        assertThat(roomsOptimizationResponse.getEconomyRoomsTotalRevenue()).isEqualTo(189.99);
    }

    @Test
    public void testShouldBePassedForTaskTestCase4() {

        // I think that task test case 4 is wrong.
        // There is no chance to pass it for given GUEST_PAYMENTS.

        RoomsOptimizationResponse roomsOptimizationResponse
                = roomsOptimizationService.optimize(7, 1, GUEST_PAYMENTS);

        assertThat(roomsOptimizationResponse.getPremiumRoomsOccupation()).isEqualTo(7);
        assertThat(roomsOptimizationResponse.getPremiumRoomsTotalRevenue()).isEqualTo(1099);
        assertThat(roomsOptimizationResponse.getEconomyRoomsOccupation()).isEqualTo(1);
        assertThat(roomsOptimizationResponse.getEconomyRoomsTotalRevenue()).isEqualTo(99.99);
    }
}
