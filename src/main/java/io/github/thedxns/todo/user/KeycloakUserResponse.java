package io.github.thedxns.todo.user;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KeycloakUserResponse {
    private String id;
    private Long createdTimestamp;
    private String username;
    private Boolean enabled;
    private Boolean totp;
    private Boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<String> disableableCredentialTypes;
    private ArrayList<String> requiredActions;
    private Integer notBefore;
    private LinkedHashMap<String, String> access;

    public KeycloakUserResponse() {
    }

    public KeycloakUserResponse(String id, Long createdTimestamp, String username, Boolean enabled, Boolean totp, Boolean emailVerified, String firstName, String lastName, String email, ArrayList<String> disableableCredentialTypes, ArrayList<String> requiredActions, Integer notBefore, LinkedHashMap<String, String> access) {
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.username = username;
        this.enabled = enabled;
        this.totp = totp;
        this.emailVerified = emailVerified;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.disableableCredentialTypes = disableableCredentialTypes;
        this.requiredActions = requiredActions;
        this.notBefore = notBefore;
        this.access = access;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getTotp() {
        return totp;
    }

    public void setTotp(Boolean totp) {
        this.totp = totp;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getDisableableCredentialTypes() {
        return disableableCredentialTypes;
    }

    public void setDisableableCredentialTypes(ArrayList<String> disableableCredentialTypes) {
        this.disableableCredentialTypes = disableableCredentialTypes;
    }

    public ArrayList<String> getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(ArrayList<String> requiredActions) {
        this.requiredActions = requiredActions;
    }

    public Integer getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Integer notBefore) {
        this.notBefore = notBefore;
    }

    public LinkedHashMap<String, String> getAccess() {
        return access;
    }

    public void setAccess(LinkedHashMap<String, String> access) {
        this.access = access;
    }
}
