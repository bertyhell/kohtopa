package data.entities;

public class Person {

    private int id;
    //TODO add building fields
    private String name;
    private String firstName;
    private String email;
    private String telephone;
    private String cellphone;

    public Person(int id, String name, String firstName, String email, String telephone, String cellphone) {
	this.id = id;
	this.name = name;
	this.firstName = firstName;
	this.email = email;
	this.telephone = telephone;
	this.cellphone = cellphone;
    }

    public String getCellphone() {
	return cellphone;
    }

    public String getEmail() {
	return email;
    }

    public String getFirstName() {
	return firstName;
    }

    public int getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getTelephone() {
	return telephone;
    }

    @Override
    public String toString() {
        return name+" "+firstName+" ("+email+")";
    }


}
