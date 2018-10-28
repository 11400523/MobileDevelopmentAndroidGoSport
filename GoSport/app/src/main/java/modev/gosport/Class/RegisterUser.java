package modev.gosport.Class;


import modev.gosport.Algorithm.HashedPassword;

public class RegisterUser {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public RegisterUser(){

    }
    public RegisterUser(String id, String firstName, String lastName, String email, String password){
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public RegisterUser(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = setPasswordHash(password);
    }

    public String getId() { return userId;}
    public void setId(String id) { this.userId = id;}
    public String getFirstName() { return firstName;}
    public void setFirstName(String firstName) { this.firstName = firstName;}
    public String getLastName() { return lastName;}
    public void setLastName(String lastName) { this.lastName = lastName;}
    public String getEmail() { return email;}
    public void setEmail(String email) { this.email = email;}
    public String getpassword() { return password;}
    private String setPasswordHash(String password){
        String hashedPassword = updateHashedPassword(password);
        return hashedPassword;
    }
    public void setPassword(String password){
        this.password = password;
    }

    private String updateHashedPassword(String password) {
        String result = "";

        if (!password.equals("")) {
            HashedPassword hashedPassword = HashedPassword.create(password);
            result = hashedPassword.toString();
        }
        return result;
    }
}
