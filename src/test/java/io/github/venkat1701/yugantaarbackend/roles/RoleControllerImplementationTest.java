package io.github.venkat1701.yugantaarbackend.roles;

import io.github.venkat1701.yugantaarbackend.config.security.SecurityConfig;
import io.github.venkat1701.yugantaarbackend.controllers.implementation.roles.RoleControllerImplementation;
import io.github.venkat1701.yugantaarbackend.dto.roles.RoleDTO;
import io.github.venkat1701.yugantaarbackend.models.roles.Role;
import io.github.venkat1701.yugantaarbackend.services.core.roles.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleControllerImplementation.class)
@Import(SecurityConfig.class) // Add this to import test security configuration
public class RoleControllerImplementationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private Role testRole;
    private RoleDTO testRoleDTO;
    private String testRoleJson;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1L);
        testRole.setRoleName("Test Role");
        testRole.setDescription("Test Description");

        testRoleDTO = new RoleDTO();
        testRoleDTO.setRoleName("Test Role");
        testRoleDTO.setDescription("Test Description");

        testRoleJson = "{\"roleName\":\"Test Role\",\"description\":\"Test Description\"}";
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_CREATE"})  // Updated to match permission annotation
    void testCreateRole() throws Exception {
        when(roleService.registerRole(any(RoleDTO.class))).thenReturn(testRole);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/create")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRoleJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value("Test Role"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        verify(roleService).registerRole(any(RoleDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_READ"})  // Updated to match permission annotation
    void testGetAllRoles() throws Exception {
        List<Role> roles = Arrays.asList(testRole);
        when(roleService.getAll()).thenReturn(roles);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/all")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleName").value("Test Role"))
                .andExpect(jsonPath("$[0].description").value("Test Description"));

        verify(roleService).getAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_READ"})  // Updated to match permission annotation
    void testGetRoleById_WhenExists() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(testRole));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("Test Role"));

        verify(roleService).findById(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_READ"})  // Updated to match permission annotation
    void testGetRoleById_WhenNotExists() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());

        verify(roleService).findById(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_UPDATE"})  // Updated to match permission annotation
    void testUpdateRole_WhenExists() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleService.update(eq(1L), any(Role.class))).thenReturn(Optional.of(testRole));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRoleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("Test Role"));

        verify(roleService).update(eq(1L), any(Role.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_UPDATE"})  // Updated to match permission annotation
    void testUpdateRole_WhenNotExists() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRoleJson))
                .andExpect(status().isNotFound());

        verify(roleService, never()).update(eq(1L), any(Role.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_DELETE"})  // Updated to match permission annotation
    void testDeleteRole_WhenExists() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleService.delete(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(roleService).delete(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_DELETE"})  // Updated to match permission annotation
    void testDeleteRole_WhenNotExists() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());

        verify(roleService, never()).delete(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ROLE_READ"})  // Updated to match permission annotation
    void testSearchRoles() throws Exception {
        Page<Role> rolePage = new PageImpl<>(Arrays.asList(testRole));
        when(roleService.search(any(PageRequest.class))).thenReturn(rolePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/search")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "roleName,ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].roleName").value("Test Role"));

        verify(roleService).search(any(PageRequest.class));
    }
}


