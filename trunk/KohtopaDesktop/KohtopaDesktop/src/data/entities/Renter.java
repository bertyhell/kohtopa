package data.entities;

import javax.swing.ImageIcon;

/**
 *
 * @author Bert Verhelst
 */
public class Renter {

	private int id;
	private ImageIcon preview;
	private String firstName;
	private String lastName;

	public Renter(int id, ImageIcon preview, String firstName, String lastName) {
		this.id = id;
		if (preview == null) {
			this.preview = new ImageIcon(getClass().getResource("/images/dummy_person_preview.png"));
		} else {
			this.preview = preview;
		}
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
	}
}
