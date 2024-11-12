package io.github.venkat1701.yugantaarbackend.roles;

import io.github.venkat1701.yugantaarbackend.config.security.SecurityConfig;
import io.github.venkat1701.yugantaarbackend.controllers.implementation.roles.RoleControllerImplementation;
import io.github.venkat1701.yugantaarbackend.dto.roles.RoleDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.services.core.roles.RoleService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPERADMIN"})
    public void testCreateRole_SuperAdminRole() throws Exception {
        // Testing with a user who has the correct role
        doReturn(sampleRole).when(roleService).registerRole(any(RoleDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Admin\", \"description\":\"Administrator role\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value("Admin"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPERADMIN"})
    public void testGetAllRoles() throws Exception {
        List<Role> roles = Arrays.asList(sampleRole);
        when(roleService.getAll()).thenReturn(roles);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].roleName").value("Admin"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_GUEST"})
    public void testGetRoleById_Found() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(sampleRole));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("Admin"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPERADMIN"})
    public void testGetRoleById_NotFound() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPERADMIN"})
    public void testUpdateRole_SuperAdminRole() throws Exception {
        // Testing if a Super Admin can update roles
        when(roleService.findById(1L)).thenReturn(Optional.of(sampleRole));
        when(roleService.update(eq(1L), any(Role.class))).thenReturn(Optional.of(sampleRole));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Updated Admin\", \"description\":\"Updated role\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("Admin"));
    }

}
