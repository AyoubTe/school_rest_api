package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.AdminResponse;
import net.samiayoub.school.entity.Admin;
import net.samiayoub.school.mapper.AdminMapper;
import net.samiayoub.school.repository.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    // Mock the dependencies
    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminMapper adminMapper;

    // Inject mocks to the service
    @InjectMocks
    private AdminService adminService;


    @Test
    void shouldRegisterAdmin() {
        // Arrange
        Admin admin = new Admin(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "admin123", "Chief of chiefs");
        when(adminRepository.save(admin)).thenReturn(admin);

        AdminResponse adminDto = new AdminResponse(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "Chief of chiefs");
        when(adminMapper.toDto(admin)).thenReturn(adminDto);

        // Act
        AdminResponse adminResponse = adminService.registerAdmin(admin);

        // Assert
        Assertions.assertNotNull(adminResponse);
    }

    @Test
    void shouldUpdateAdmin() {
        Admin admin = new Admin(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "admin123", "Chief of chiefs");
        when(adminRepository.save(admin)).thenReturn(admin);

        AdminResponse adminDto = new AdminResponse(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "Chief of chiefs");
        when(adminMapper.toDto(admin)).thenReturn(adminDto);

        AdminResponse adminResponse = adminService.updateAdmin(admin);
        Assertions.assertNotNull(adminResponse);
    }

    @Test
    void shouldDeleteAdmin() {
        // 1. Arrange (Préparation)
        Admin admin = new Admin(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "admin123", "Chief of chiefs");

        // 2. Act (Action : on appelle la méthode du SERVICE, pas du repository)
        adminService.deleteAdmin(admin);

        // 3. Assert (Vérification)
        // On demande à Mockito : "Est-ce que la méthode delete() du repository a bien été appelée 1 fois avec cet admin ?"
        verify(adminRepository, times(1)).delete(admin);
    }

    @Test
    void shouldDeleteAdminById() {
        // 1. Arrange
        Long adminId = 1L;

        // 2. Act
        adminService.deleteAdminById(adminId);

        // 3. Assert
        verify(adminRepository, times(1)).deleteById(adminId);
    }

    @Test
    void shouldGetAllAdmins() {
        // Arrange
        Admin admin1 = new Admin(1L, "admin1", "Admin", "1", "admi1@school.net", "passw@rd1", "Mission 1");
        Admin admin2 = new Admin(2L, "admin2", "Admin", "2", "admi2@school.net", "passw@rd1", "Mission 2");
        Admin admin3 = new Admin(3L, "admin3", "Admin", "3", "admi3@school.net", "passw@rd1", "Mission 3");
        // Mock call result
        when(adminRepository.findAll()).thenReturn(List.of(admin1, admin2, admin3));

        AdminResponse adminResponse1 = new AdminResponse(1L, "admin1", "Admin", "1", "admi1@school.net", "Mission 1");
        AdminResponse adminResponse2 = new AdminResponse(2L, "admin2", "Admin", "2", "admi2@school.net", "Mission 2");
        AdminResponse adminResponse3 = new AdminResponse(3L, "admin3", "Admin", "3", "admi3@school.net", "Mission 3");

        when(adminMapper.toDtoList(adminRepository.findAll())).thenReturn(List.of(adminResponse1, adminResponse2, adminResponse3));

        List<AdminResponse> admins = adminService.getAllAdmins();

        assertThat(admins).hasSize(3);
    }

    @Test
    void shouldGetAdminById() {
        Admin admin = new Admin(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "admin123", "Chief of chiefs");
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        AdminResponse adminResponse = new AdminResponse(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "Chief of chiefs");
        when(adminMapper.toDto(admin)).thenReturn(adminResponse);

        AdminResponse adminDto = adminService.getAdminById(1L);
        Assertions.assertNotNull(adminDto);
    }

    @Test
    void shouldGetAdminByUsernameOrEmail() {
        Admin admin = new Admin(1L, "admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "admin123", "Chief of chiefs");
        when(adminRepository.findByUsernameOrEmail("admin.applicatif", "admin.applicatif")).thenReturn(Optional.of(admin));
        Admin adminResponse = adminService.getAdminByUsernameOrEmail("admin.applicatif");
        Assertions.assertNotNull(adminResponse);
    }
}