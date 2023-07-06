package com.epam.demo.pbl.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "mobile_number")
    private long mobileNumber;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean active;

    public UserDetails() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return id == that.id &&
                mobileNumber == that.mobileNumber &&
                active == that.active &&
                firstName.equals(that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                emailId.equals(that.emailId) &&
                bankAccountNumber.equals(that.bankAccountNumber) &&
                panNumber.equals(that.panNumber) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, emailId, mobileNumber, bankAccountNumber, panNumber, password, active);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + active +
                '}';
    }
}
