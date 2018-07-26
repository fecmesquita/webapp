package br.org.cip.CRMMock.model.theme;

public enum ThemeType {
	
	CRM("crm"), WEB("web");

	private final String theme;

	private ThemeType(String label) {
		this.theme = label;
	}

	public String getLabel() {
		return theme;
	}
	
	
	
}
