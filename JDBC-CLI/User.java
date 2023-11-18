import java.sql.Date;

public class User {
    private String userID;
    private String userName;
    private String realName;
    private String email;
    private String password;
    private String gender;
    private Date dateOfBirth;
    private String location;
    private String nationality;
    private Boolean admin;

    // Constructor
    public User(String userID, String userName, String realName, String email,
                String password, String gender, Date dateOfBirth, String location, String nationality, Boolean admin) {
        this.userID = userID;
        this.userName = userName;
        this.realName = realName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
        this.nationality = nationality;
        this.admin = admin;
    }

    // Getters and Setters
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }    
    
    public Boolean getAdmin() { return admin; }
    public void setAdmin(Boolean admin) { this.admin = admin; }

    // Overriding the toString() method for debugging purposes
    @Override
    public String toString() {
        return "<User Credential>\n" +
                "userID=" + userID + 
                ", password=" + password +
                ", userName=" + userName + 
                ", realName=" + realName + 
                ", email=" + email +  "\n" +
                "gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", location=" + location +
                ", nationality=" + nationality + "\n";
    }
}