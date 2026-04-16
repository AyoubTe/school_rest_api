package net.samiayoub.school.controller;

import net.samiayoub.school.dto.requets.AdminRequest;
import net.samiayoub.school.dto.responses.AdminResponse;
import net.samiayoub.school.mapper.AdminMapper;
import net.samiayoub.school.repository.AdminRepository;
import net.samiayoub.school.security.jwt.JwtTokenProvider;
import net.samiayoub.school.service.AdminService;
import net.samiayoub.school.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminMapper adminMapper;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private AdminService adminService;

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void shouldGetAdmins() throws Exception {
        AdminResponse adminResponse1 = new AdminResponse(1L, "admin1", "Admin", "1", "admi1@school.net", "Mission 1");
        AdminResponse adminResponse2 = new AdminResponse(2L, "admin2", "Admin", "2", "admi2@school.net", "Mission 2");
        AdminResponse adminResponse3 = new AdminResponse(3L, "admin3", "Admin", "3", "admi3@school.net", "Mission 3");
        when(adminService.getAllAdmins()).thenReturn(List.of(adminResponse1, adminResponse2, adminResponse3));

        mockMvc.perform(get("/api/v1/admins"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void shouldCreateAdmin() throws Exception {
        AdminRequest request = new AdminRequest("admin.applicatif", "Admin", "APPLICATIF", "admin.applicatif@school.net", "HashedPassword", "Chief of the chiefs");
        AdminResponse response = new AdminResponse(1L, "admin.applicatif", "Admin", "APPLICATIF",
                "admin.applicatif@school.net", "Chief of the chiefs");
        when(adminService.registerAdmin(adminMapper.toEntity(request))).thenReturn(response);

        mockMvc.perform(post("/api/v1/admins")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "admin.applicatif",
                                  "firstname": "Admin",
                                  "lastname": "APPLICATIF",
                                  "email": "admin.applicatif@school.net",
                                  "password": "HashedPassword",
                                  "mission": "Chief of the chiefs"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("admin.applicatif"));
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void shouldGetAdminById() throws Exception {
        AdminResponse response = new AdminResponse(1L, "admin1", "Admin", "1", "admi1@school.net", "Mission 1");
        when(adminService.getAdminById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/admins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("admin1"));
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void shouldDeleteAdminById() throws Exception {
        mockMvc.perform(delete("/api/v1/admins/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}