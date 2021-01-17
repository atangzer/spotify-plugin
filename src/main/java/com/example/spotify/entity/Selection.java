package com.example.spotify.entity;

import javax.validation.constraints.Min;

public class Selection {
	@Min(value = 1, message = "Enter a value greater than 0!")
	private int rows;
	private int columns;
	private int n;
	private int displayH;
	private int displayW;
	
	public int getRows() {
		return rows;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public int getDisplayH() {
		return displayH;
	}
	
	public void setDisplayH(int displayH) {
		this.displayH = displayH;
	}
	
	public int getDisplayW() {
		return displayW;
	}
	
	public void setDisplayW(int displayW) {
		this.displayW = displayW;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
}
