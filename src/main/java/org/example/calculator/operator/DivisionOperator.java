package org.example.calculator.operator;

import org.example.calculator.domain.NewArithmeticOperator;
import org.example.calculator.domain.PositiveNumber;

public class DivisionOperator implements NewArithmeticOperator {
    @Override
    public boolean supports(String operator) {
        return "/".equals(operator);
    }

    @Override
    public int calculate(PositiveNumber operand1, PositiveNumber operand2) {
//        if (operand2.toInt() == 0) {
//            throw new IllegalArgumentException("0으로 나눌수 없습니다.");
//        }
        return operand1.toInt() / operand2.toInt();
    }
}
