package com.mpsystems.productioncontrol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyDTO {

    @NotBlank(message = "Nome é obrigatório!") //@NotBlank: Garante que o campo não seja nulo e não esteja em branco.
    private String name;

    @NotBlank(message = "CNPJ é obrigatório!")
    private String cnpj;

    @NotBlank(message = "Endereço é obrigatório!")
    private String address;

    @NotBlank(message = "Número de telefone é obrigatório!")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    //@Size: Define o tamanho mínimo e máximo do campo.
    private String phoneNumber;

    @NotBlank(message = "Email é obrigatório!")
    @Email(message = "Invalid email format") //@Email: Verifica se o campo possui um formato de e-mail válido.
    private String responsibleEmail;

    @NotBlank(message = "Password é obrigatório!")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResponsibleEmail() {
        return responsibleEmail;
    }

    public void setResponsibleEmail(String responsibleEmail) {
        this.responsibleEmail = responsibleEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}