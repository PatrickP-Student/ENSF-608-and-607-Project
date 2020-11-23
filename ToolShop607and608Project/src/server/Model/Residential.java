package server.Model;

public class Residential extends Customer {
	
	private static final long serialVersionUID = 32L;
	private String type;

	public Residential(int customerID, String firstName, String lastName, String address, String postalCode,String phoneNumber, String type) {
		super(customerID, firstName, lastName, address, postalCode, phoneNumber);
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    public String toString() {
        return "".format("\nCustomer Name: %s %s\nCustomer ID: %d\nCustomer Type: %s\nCustomer Address: %s %s\nCustomer Phone Number: %s\n",
        		this.getFirstName(),this.getLastName(),this.getCustomerID(),this.getType(),this.getAddress(),this.getPostalCode(),
        		this.getPhoneNumber());
    }

}
