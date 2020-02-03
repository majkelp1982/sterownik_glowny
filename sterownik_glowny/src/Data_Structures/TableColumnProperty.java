package Data_Structures;

public class TableColumnProperty {
	private String columnName;
	private String columnTyp;
	
	public String getColumnName() {
		return columnName;
	}
	
	public String getColumnTyp() {
		return columnTyp;
	}

	public TableColumnProperty(String columnName, String columnTyp) {
		this.columnName = columnName;
		this.columnTyp = columnTyp;
	}
}
