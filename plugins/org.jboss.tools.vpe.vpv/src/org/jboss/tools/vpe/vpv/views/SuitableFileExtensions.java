package org.jboss.tools.vpe.vpv.views;

public enum SuitableFileExtensions {
	HTML("html"), //$NON-NLS-1$
	HTM("htm"), //$NON-NLS-1$
	XHTML("xhtml"), //$NON-NLS-1$
	JSP("jsp"); //$NON-NLS-1$

	private final String value;

	private SuitableFileExtensions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static boolean contains(String fileExtension) {
		for (SuitableFileExtensions extension : SuitableFileExtensions.values()) {
			if (extension.value.equals(fileExtension)) {
				return true;
			}
		}
		return false;
	}

}