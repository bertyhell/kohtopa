package Exceptions;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class PersonNotFoundException extends Exception{

	public PersonNotFoundException(String message) {
		super("PersonNotFoundException: " + message);
	}
}
