package com.bdua.students.Service

import com.bdua.students.Model.Admin
import com.bdua.students.Repository.AdminRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val adminRepository: AdminRepository
) {
    fun getAllAdmins(): List<Admin> = adminRepository.findAll()

    fun getAdminById(id: Int): Admin = adminRepository.findById(id).orElseThrow()

    fun createAdmin(admin: Admin): Admin {
        admin.id = null
        return adminRepository.save(admin)
    }

    fun deleteAdmin(id: Int): String {
        return if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id)
            "Admin with ID $id deleted successfully"
        } else {
            "Admin with ID $id not found"
        }
    }
}
