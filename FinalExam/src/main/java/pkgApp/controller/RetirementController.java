package pkgApp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javafx.beans.value.*;

import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

	private RetirementApp mainApp = null;
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtWhatYouNeedToSave;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;

	private HashMap<TextField, String> hmTextFieldRegEx = new HashMap<TextField, String>();

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Adding an entry in the hashmap for each TextField control I want to validate
		// with a regular expression
		// "\\d*?" - means any decimal number
		// "\\d*(\\.\\d*)?" means any decimal, then optionally a period (.), then
		// decmial
		hmTextFieldRegEx.put(txtYearsToWork, "\\d*?");
		hmTextFieldRegEx.put(txtAnnualReturnWorking, "\\d*(\\.\\d*)?");
		hmTextFieldRegEx.put(txtYearsRetired,"\\d*?");	
		hmTextFieldRegEx.put(txtAnnualReturnRetired,"\\d*(\\.\\d*)?");
		hmTextFieldRegEx.put(txtRequiredIncome,"\\d*?");
		hmTextFieldRegEx.put(txtMonthlySSI,"\\d*?");
		// Check out these pages (how to validate controls):
		// https://stackoverflow.com/questions/30935279/javafx-input-validation-textfield
		// https://stackoverflow.com/questions/40485521/javafx-textfield-validation-decimal-value?rq=1
		// https://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
		// There are some examples on how to validate / check format

		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			String strRegEx = (String) pair.getValue();

			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					// If newPropertyValue = true, then the field HAS FOCUS
					// If newPropertyValue = false, then field HAS LOST FOCUS
					if (!newPropertyValue) {
						if (!txtField.getText().matches(strRegEx)) {
							txtField.setText("");
							txtField.requestFocus();
						}
					}
				}
			});
		}

	}

	@FXML
	public void btnClear(ActionEvent event) {
		System.out.println("Clear pressed");

		// disable read-only controls
		txtSaveEachMonth.setDisable(true);
		txtWhatYouNeedToSave.setDisable(true);

		// Clear, enable txtYearsToWork
		txtYearsToWork.clear();
		txtYearsToWork.setDisable(false);
		
		txtAnnualReturnWorking.clear();
		txtAnnualReturnWorking.setDisable(false);
		
		txtWhatYouNeedToSave.clear();
		txtWhatYouNeedToSave.setDisable(true);
		
		txtYearsRetired.clear();
		txtYearsRetired.setDisable(false);
		
		txtAnnualReturnRetired.clear();
		txtAnnualReturnRetired.setDisable(false);
		
		txtRequiredIncome.clear();
		txtRequiredIncome.setDisable(false);
		
		txtMonthlySSI.clear();
		txtMonthlySSI.setDisable(false);
		
	}

	@FXML
	public void btnCalculate() {

		System.out.println("calculating");
		txtSaveEachMonth.setDisable(false);
		txtWhatYouNeedToSave.setDisable(false);
		
		double dAnnualReturnRetired = Double.parseDouble(txtAnnualReturnRetired.getText());
		int iYearsRetired = Integer.parseInt(txtYearsRetired.getText());
		double dRequiredIncome = Double.parseDouble(txtRequiredIncome.getText());
		double dMonthlySSI = Double.parseDouble(txtMonthlySSI.getText());
		double dAnnualReturnWorking = Double.parseDouble(txtAnnualReturnWorking.getText());
		int iYearsToWork = Integer.parseInt(txtYearsToWork.getText());
		Retirement retirement = new Retirement(iYearsToWork, dAnnualReturnWorking, iYearsRetired,
		dAnnualReturnRetired, dRequiredIncome, dMonthlySSI);
		
		double pv = retirement.TotalAmountToSave();
		double pmt =retirement.MonthlySavings();
		txtWhatYouNeedToSave.setText(Double.toString(Math.abs(pv)));
		txtSaveEachMonth.setText(Double.toString(pmt));
		txtYearsToWork.setDisable(true);
		txtAnnualReturnWorking.setDisable(true);
		txtYearsRetired.setDisable(true);
		txtAnnualReturnRetired.setDisable(true);
		txtRequiredIncome.setDisable(true);
		txtMonthlySSI.setDisable(true);
		
	}
}
