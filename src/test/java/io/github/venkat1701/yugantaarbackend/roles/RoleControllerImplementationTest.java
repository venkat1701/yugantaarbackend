package io.github.venkat1701.yugantaarbackend.roles;

import io.github.venkat1701.yugantaarbackend.config.security.SecurityConfig;
import io.github.venkat1701.yugantaarbackend.controllers.implementation.roles.RoleControllerImplementation;
import io.github.venkat1701.yugantaarbackend.dto.roles.RoleDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.services.core.roles.RoleService;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.PermissionsUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleControllerImplementation.class)
@ImportAutoConfiguration(SecurityConfig.class)
public class RoleControllerImplementationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private Role sampleRole;
    private RoleDTO sampleRoleDTO;

    @BeforeEach
    public void setup() {
        sampleRole = new Role();
        sampleRole.setId(1L);
        sampleRole.setRoleName("Admin");
        sampleRole.setDescription("Administrator role");

        sampleRoleDTO = new RoleDTO("Admin", "Administrator role");
    }

    // Test successful creation with proper authority
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPERADMIN"})
    public void testCreateRole_WithSuperAdminRole_ShouldSucceed() throws Exception {
        when(roleService.registerRole(any(RoleDTO.class))).thenReturn(sampleRole);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Admin\", \"description\":\"Administrator role\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value("Admin"));
    }

    // Test creation with insufficient authority
    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_GUEST"})
    public void testCreateRole_WithGuestRole_ShouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Admin\", \"description\":\"Administrator role\"}"))
                .andExpect(status().isForbidden());
    }

    // Test successful retrieval with proper authority
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPERADMIN"})
    public void testGetAllRoles_WithSuperAdminRole_ShouldSucceed() throws Exception {
        List<Role> roles = Arrays.asList(sampleRole);
        when(roleService.getAll()).thenReturn(roles);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // Test retrieval with insufficient authority
    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_GUEST"})
    public void testGetAllRoles_WithGuestRole_ShouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/all"))
                .andExpect(status().isForbidden());
    }

    // Test successful update with proper authority
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPERADMIN"})
    public void testUpdateRole_WithSuperAdminRole_ShouldSucceed() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(sampleRole));
        when(roleService.update(eq(1L), any(Role.class))).thenReturn(Optional.of(sampleRole));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Updated Admin\", \"description\":\"Updated role\"}"))
                .andExpect(status().isOk());
    }

    // Test update with insufficient authority
    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_GUEST"})
    public void testUpdateRole_WithGuestRole_ShouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Updated Admin\", \"description\":\"Updated role\"}"))
                .andExpect(status().isForbidden());
    }

    // Test without authentication
    @Test
    public void testEndpoints_WithoutAuthentication_ShouldFail() throws Exception {
        // Test create endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Admin\", \"description\":\"Administrator role\"}"))
                .andExpect(status().isUnauthorized());

        // Test get all endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/all"))
                .andExpect(status().isUnauthorized());

        // Test update endpoint
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Updated Admin\", \"description\":\"Updated role\"}"))
                .andExpect(status().isUnauthorized());
    }
}