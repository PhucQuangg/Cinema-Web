package com.example.cinema_back_end.apis.admin;

import java.util.List;

import com.example.cinema_back_end.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cinema_back_end.dtos.BranchDTO;
import com.example.cinema_back_end.services.IBranchService;

@RestController
@RequestMapping("/api/admin/branches")
@CrossOrigin(value = "*")

public class ManageBranchAPI {
	@Autowired
	private BranchService branchService;
	@GetMapping
	public ResponseEntity<List<BranchDTO>> getAllBranches(){
		return new ResponseEntity<>(branchService.findAll(),HttpStatus.OK);
	}
	@PostMapping
    public ResponseEntity<String> addBranch(@RequestBody BranchDTO branch){
    	branchService.update(branch);
        return  new ResponseEntity<String>("Thêm chi nhánh thành công!" ,HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> updateBranch(@RequestBody BranchDTO branch){
    	branchService.update(branch);
        return  new ResponseEntity<String>("Cập nhật chi nhánh thành công!" ,HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteBranch(@Param("branchId") Integer branchId){
    	branchService.remove(branchId);
    	return new ResponseEntity<String>("Xóa chi nhánh thành công!", HttpStatus.OK);
    }
}
