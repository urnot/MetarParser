package com.weather.metar.exception;

public class MetarParseException extends Exception {

	protected String record = null;

	public MetarParseException() {
		super();
	}

	public MetarParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MetarParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetarParseException(String message) {
		super(message);
	}

	public MetarParseException(Throwable cause) {
		super(cause);
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

}
