package com.walgula.hotel.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.walgula.hotel.dto.RoomsRequest;
import com.walgula.hotel.model.RoomsOptimizationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomsControllerTest {
    private RoomsRequest request;
    private final List<Double> GUEST_PAYMENTS
            = List.of(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void init() {
        request = new RoomsRequest();
    }

    @Test
    public void testShouldBePassedForValidRequest() {
        // given
        request.setAvailablePremiumRooms(3);
        request.setAvailableEconomyRooms(3);
        request.setGuestsPayments(GUEST_PAYMENTS);

        // when
        ResponseEntity<RoomsOptimizationResponse> response
                = restTemplate.postForEntity("/api/v1/rooms/optimize", request, RoomsOptimizationResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RoomsOptimizationResponse roomsOptimizationResponse = response.getBody();
        assertThat(roomsOptimizationResponse).isNotNull();
        assertThat(roomsOptimizationResponse.getPremiumRoomsOccupation()).isEqualTo(3);
        assertThat(roomsOptimizationResponse.getPremiumRoomsTotalRevenue()).isEqualTo(738.0);
        assertThat(roomsOptimizationResponse.getEconomyRoomsOccupation()).isEqualTo(3);
        assertThat(roomsOptimizationResponse.getEconomyRoomsTotalRevenue()).isEqualTo(167.99);
    }

    @Test
    public void apiShouldRaiseBadRequestStatusForInvalidAvailablePremiumRoomsValue() {
        // given
        request.setAvailablePremiumRooms(-3);
        request.setAvailableEconomyRooms(3);
        request.setGuestsPayments(GUEST_PAYMENTS);

        // when
        ResponseEntity<String> response
                = restTemplate.postForEntity("/api/v1/rooms/optimize", request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Room numbers must be greater than zero.");
    }

    @Test
    public void apiShouldRaiseBadRequestStatusForInvalidAvailableEconomyRoomsValue() {
        // given
        request.setAvailablePremiumRooms(3);
        request.setAvailableEconomyRooms(-3);
        request.setGuestsPayments(GUEST_PAYMENTS);

        // when
        ResponseEntity<String> response
                = restTemplate.postForEntity("/api/v1/rooms/optimize", request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Room numbers must be greater than zero.");
    }

    @Test
    public void apiShouldRaiseBadRequestStatusForInvalidGuestsPaymentsValue() {
        // given
        request.setAvailablePremiumRooms(3);
        request.setAvailableEconomyRooms(3);
        request.setGuestsPayments(List.of(23.0, -3.0, 6.0));

        // when
        ResponseEntity<String> response
                = restTemplate.postForEntity("/api/v1/rooms/optimize", request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Payments must be greater than zero.");
    }

    @Test
    public void apiShouldRaiseBadRequestStatusForEmptyGuestsPaymentsValue() {
        // given
        request.setAvailablePremiumRooms(3);
        request.setAvailableEconomyRooms(3);
        request.setGuestsPayments(new ArrayList<Double>());

        // when
        ResponseEntity<String> response
                = restTemplate.postForEntity("/api/v1/rooms/optimize", request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Payments must not be empty.");
    }
}
