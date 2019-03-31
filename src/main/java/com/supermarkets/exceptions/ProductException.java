package com.supermarkets.exceptions;

public class ProductException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@link ProductException} with the specified
	 * detail message. A detail message is a String that describes this
	 * particular exception.
	 *
	 * @param message the detail message (which is saved for later retrieval
         * by the {@link Throwable#getMessage()} method).
	 */
	public ProductException(String message) {
		super(message);
	}


	/**
	 * Constructs a new {@link ProductException} with the specified
	 * detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval
         * by the {@link Throwable#getMessage()} method).
	 *
	 * @param cause The cause (which is saved for later retrieval by the
	 * {@link #getCause()} method). (A null value is permitted, and
	 * indicates that the cause is nonexistent or unknown.)
	 */
	public ProductException(String message, Throwable cause) {
		super(message, cause);
	}
}
