package pkgEmpty;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgCore.Retirement;

import org.apache.poi.ss.formula.functions.*;

public class TestFinance {
	int iYearsToWork = 40;
	double dAnnualReturnWorking = 0.07;
	int iYearsRetired = 20;
	double dAnnualReturnRetired = 0.02;
	double dRequiredIncome = 10000;
	double dMonthlySSI = 2642;
	@Test
	public void TestPV() {


		double PV = Retirement.PV(dAnnualReturnRetired / 12, iYearsRetired * 12, dRequiredIncome - dMonthlySSI, 0, false);
		
		System.out.println(PV);
		
		//	In my calculations, in order to receive a payment of $7358 ($10000-2642), if you were making 2% on your return, and you wanted it paid off
		//	over a 20 year period... You'd need to save $1,454,485.55.
		
		//	Accounting rules- PV returns a negative number if you pass in a positive PMT amount.  Take the absolute value
		
		//	I want to copmare a double with a double... Doubles are not precice... I have to give a rounding factor.
		//	Note the third argument.  That says only compare the double values to the hundredth place.
		assertEquals(1454485.55,Math.abs(PV),0.01);
		
	}

	@Test
	public void TestPMT() {
		Retirement retirement = new Retirement(iYearsToWork, dAnnualReturnWorking, iYearsRetired,
				dAnnualReturnRetired, dRequiredIncome, dMonthlySSI);
		double pmt =retirement.MonthlySavings();
		System.out.println(pmt);
		assertEquals(3620.57,Math.abs(pmt),0.01);
		//TODO: Test PMT.  Make sure PMT works as expected.
	}
}
