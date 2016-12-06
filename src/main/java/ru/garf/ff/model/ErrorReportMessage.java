package ru.garf.ff.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorReportMessage extends ReportMessage {

	private List<String> error;

	public ErrorReportMessage() {
		this.success=false;
		this.error = new ArrayList<String>();

	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}
	
	public ErrorReportMessage addError(String error) {
		this.error.add(error);
		return this;
	} 
	
	
	
}
