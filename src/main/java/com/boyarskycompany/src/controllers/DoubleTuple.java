package com.boyarskycompany.src.controllers;

/**
 * Created by zatov on 06.11.2016.
 */
public class DoubleTuple<T, W> {
	private T tField;
	private W wFiels;
	
	public DoubleTuple(T tField, W wFiels) {
		this.tField = tField;
		this.wFiels = wFiels;
	}
	
	public T gettField() {
		return tField;
	}
	
	public W getwFiels() {
		return wFiels;
	}
	
	public void settField(T tField) {
		this.tField = tField;
	}
	
	public void setwFiels(W wFiels) {
		this.wFiels = wFiels;
	}
}
