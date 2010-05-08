package Exceptions;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class WrongNumberOfSelectedItemsException extends Exception{

	public WrongNumberOfSelectedItemsException(String message) {
		super("WrongNumberOfSelectedItemsException: " + message);
	}
}
