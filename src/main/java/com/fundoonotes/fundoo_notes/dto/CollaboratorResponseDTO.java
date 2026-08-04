package com.fundoonotes.fundoo_notes.dto;

public class CollaboratorResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String permission;

    public CollaboratorResponseDTO() {
    }

    public CollaboratorResponseDTO(Long id, String email, String firstName,
                                    String lastName, String permission) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
