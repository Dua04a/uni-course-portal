package com.bdua.students.Controller

import com.bdua.students.Model.Admin
import com.bdua.students.Service.AdminService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admins")
class AdminController(
    private val adminService: AdminService
) {
    @GetMapping
    fun getAllAdmins(): List<Admin> = adminService.getAllAdmins()

    @GetMapping("/{id}")
    fun getAdminById(@PathVariable id: Int): Admin = adminService.getAdminById(id)

    @PostMapping
    fun createAdmin(@RequestBody admin: Admin): Admin = adminService.createAdmin(admin)

    @DeleteMapping("/{id}")
    fun deleteAdmin(@PathVariable id: Int): String = adminService.deleteAdmin(id)
}
