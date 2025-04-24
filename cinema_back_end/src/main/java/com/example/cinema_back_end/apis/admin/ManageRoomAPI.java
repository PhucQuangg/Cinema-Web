package com.example.cinema_back_end.apis.admin;

import java.util.List;

import com.example.cinema_back_end.services.RoomService;
import com.example.cinema_back_end.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.cinema_back_end.dtos.RoomDTO;
import com.example.cinema_back_end.dtos.SeatDTO;
import com.example.cinema_back_end.services.IRoomService;
import com.example.cinema_back_end.services.ISeatService;

@RestController
@RequestMapping("/api/admin/rooms")
@CrossOrigin(value = "*")

public class ManageRoomAPI {
	@Autowired
	private RoomService roomSerive;
	@Autowired
	private SeatService seatSerive;
	@PostMapping
	public ResponseEntity<List<RoomDTO>> getRoomByBranch(@Param("branchId") Integer branchId){
		return new ResponseEntity<>(roomSerive.getRoomsByBranch(branchId),HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<String> addRoom(@RequestBody RoomDTO room){
		try {
			Integer roomId=roomSerive.add(room).getId();
			seatSerive.addAllSeat(room.getSeats(),roomId);
			return new ResponseEntity<>("Thêm phòng thành công!",HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>("Có lỗi xảy ra!",HttpStatus.BAD_REQUEST);
		}
		
	}
	@PutMapping
	public ResponseEntity<String> updateRoom(@RequestBody RoomDTO room){
		try {
			roomSerive.update(room);
			seatSerive.updateSeats(room.getSeats());
			return new ResponseEntity<>("Cập nhật thành công!",HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>("Có lỗi xảy ra, không thể cập nhật!",HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping
	public ResponseEntity<String> deleteRoom(@RequestParam Integer roomId){
		roomSerive.remove(roomId);
		try {
			return new ResponseEntity<>("Xóa phòng thành công!",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Có lỗi, Xóa phòng thất bại!",HttpStatus.BAD_REQUEST);
		}
		
	}
}
