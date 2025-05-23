package com.example.cinema_back_end.apis.admin;

import java.util.List;

import com.example.cinema_back_end.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinema_back_end.entities.Role;
import com.example.cinema_back_end.security.service.IRoleService;

@RestController
@RequestMapping("/api/admin/roles")
@CrossOrigin(value = "*")

public class ManageRoleAPI {
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ResponseEntity<List<Role>> getAllRoles(){
		return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
	}

}
