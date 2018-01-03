package com.boyarskycompany.mcis.util;

/**
 * Created by zatov on 06.11.2016.
 */
public class DoubleTuple<T, W> {
	private T firstField;
	private W secondField;
	
	public DoubleTuple(T firstField, W secondField) {
		this.firstField = firstField;
		this.secondField = secondField;
	}
	
	public T getFirstField() {
		return firstField;
	}
	
	public W getSecondField() {
		return secondField;
	}
	
	public void setFirstField(T firstField) {
		this.firstField = firstField;
	}
	
	public void setSecondField(W secondField) {
		this.secondField = secondField;
	}
}
