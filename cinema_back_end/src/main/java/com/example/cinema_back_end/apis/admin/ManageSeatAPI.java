package com.example.cinema_back_end.apis.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cinema_back_end.dtos.SeatDTO;
import com.example.cinema_back_end.services.ISeatService;

@RestController
@RequestMapping(value = "/api/admin/seats", produces = "application/json")
@CrossOrigin(value = "*")

public class ManageSeatAPI {
	@Autowired
	private ISeatService seatService;
	@PostMapping
	public ResponseEntity<List<SeatDTO>> getAllseatsByRoom(@Param("roomId") Integer roomId) {
		return new ResponseEntity<>(seatService.getAllSeatByRoom(roomId),HttpStatus.OK);
	}
	
}
