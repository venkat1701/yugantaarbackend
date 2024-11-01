package io.github.venkat1701.yugantaarbackend.models.roles;


import jakarta.persistence.*;

@Entity
@Table(name="rolespermissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int roleId;
    private int permissionId;
    private String createdAt;
    private String updatedAt;
}
