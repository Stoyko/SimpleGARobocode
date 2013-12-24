package id.ac.its.SimpleGARobocode;

import java.util.List;

public class TableREX {
	final int numRows = 44;		// Sementara di fiksasi seperti ini
									// Jadi setiap masukan terdiri dari 7-bit (16 + 44)
	List <Row> tableRow;
	
	public void clearList() {
		tableRow.clear();
	}
	
	public void inputFromGnome(String gnome) {
		this.clearList();
		String[] rowGnome = gnome.split("(?<=\\G.{16})");
		for(String row: rowGnome) {
			Input input1, input2;
			Row.Operator operator = Row.Operator.values()[Integer.parseInt(row.substring(0, 3), 2)];
			int input1int = Integer.parseInt(row.substring(4, 9), 2);
			int input2int = Integer.parseInt(row.substring(10, 15), 2);
			
			if(input1int >= 17) {
				input1 = new Input(Input.InputType.previousLine.ordinal(), input1int - 17);
			}
			else {
				input1 = new Input(input1int);
			}
			
			if(input2int >= 17) {
				input2 = new Input(Input.InputType.previousLine.ordinal(), input2int - 17);
			} 
			else {
				input2 = new Input(input2int);
			}
			
			Row newRow = new Row(operator, input1, input2);
			tableRow.add(newRow);
		}	
	}
}
