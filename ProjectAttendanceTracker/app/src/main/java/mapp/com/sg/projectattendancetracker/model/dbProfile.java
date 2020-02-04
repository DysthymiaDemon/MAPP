package mapp.com.sg.projectattendancetracker.model;

import android.location.Location;

import java.sql.Timestamp;

public class dbProfile {
    private Timestamp birthDate;
    private int childcareLeave;
    private String email;
    private int emergencyLeave;
    private String firstName;
    private String jobAddress;
    private Location jobLatLong;
    private String jobPlaceName;
    private String jobTitle;
    private String lastName;
    private int maxAnnual;
    private int mcPerMonth;
    private int parentalLeave;
    private int salaryTier;

    public dbProfile(){}

    public dbProfile(Timestamp birthDate, int childcareLeave, String email, int emergencyLeave, String firstName, String jobAddress, Location jobLatLong, String jobPlaceName, String jobTitle, String lastName, int maxAnnual, int mcPerMonth, int parentalLeave, int salaryTier) {
        this.birthDate = birthDate;
        this.childcareLeave = childcareLeave;
        this.email = email;
        this.emergencyLeave = emergencyLeave;
        this.firstName = firstName;
        this.jobAddress = jobAddress;
        this.jobLatLong = jobLatLong;
        this.jobPlaceName = jobPlaceName;
        this.jobTitle = jobTitle;
        this.lastName = lastName;
        this.maxAnnual = maxAnnual;
        this.mcPerMonth = mcPerMonth;
        this.parentalLeave = parentalLeave;
        this.salaryTier = salaryTier;
    }

    //get methods
    public Timestamp getBirthDate() {
        return birthDate;
    }

    public int getChildcareleave() {
        return childcareLeave;
    }

    public String getEmail() {
        return email;
    }

    public int getEmergencyLeave() {
        return emergencyLeave;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public Location getJobLatLong() {
        return jobLatLong;
    }

    public String getJobPlaceName() {
        return jobPlaceName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLastName() {
        return lastName;
    }

    public int getMaxAnnual() {
        return maxAnnual;
    }

    public int getMcPerMonth() {
        return mcPerMonth;
    }

    public int getParentalLeave() {
        return parentalLeave;
    }

    public int getSalaryTier() {
        return salaryTier;
    }

    //set methods
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public void setChildcareleave(int childcareLeave) {
        this.childcareLeave = childcareLeave;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmergencyLeave(int emergencyLeave) {
        this.emergencyLeave = emergencyLeave;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setJobAddress(String jobAddress) {
        this.jobAddress = jobAddress;
    }

    public void setJobLatLong(Location jobLatLong) {
        this.jobLatLong = jobLatLong;
    }

    public void setJobPlaceName(String jobPlaceName) {
        this.jobPlaceName = jobPlaceName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMaxAnnual(int maxAnnual) {
        this.maxAnnual = maxAnnual;
    }

    public void setMcPerMonth(int mcPerMonth) {
        this.mcPerMonth = mcPerMonth;
    }

    public void setParentalLeave(int parentalLeave) {
        this.parentalLeave = parentalLeave;
    }

    public void setSalaryTier(int salaryTier) {
        this.salaryTier = salaryTier;
    }
}
