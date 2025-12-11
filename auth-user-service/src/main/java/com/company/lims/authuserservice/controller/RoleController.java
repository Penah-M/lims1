package com.company.lims.authuserservice.controller;

import com.company.lims.authuserservice.dto.request.RoleRequest;
import com.company.lims.authuserservice.dto.response.RoleResponse;
import com.company.lims.authuserservice.enums.RoleName;
import com.company.lims.authuserservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/roles")
public class RoleController {

    RoleService roleService;

    @PostMapping("create")
    public RoleResponse createRole( @RequestBody RoleRequest request) {
        return roleService.createRole(request);
    }

    @GetMapping("delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }

    @GetMapping("getAllRoles")
    public List<RoleResponse> getAll(){
        return roleService.getAll();
    }

    @PutMapping("deleteRole/{name}")
    public void deleteRole(@PathVariable RoleName name){
        roleService.deleteRole(name);
    }
}
