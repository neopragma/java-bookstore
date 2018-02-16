package com.neopragma.contracts;

import java.text.MessageFormat;

/**
 * Provide support for asserting preconditions and postconditions.
 *
 * call
 * <ul>
 * <li>Contract.require(boolean expression, String message)</li>
 * <li>Contract.ensure(boolean expression, String message)</li>
 * </ul>
 * If the boolean expression is not true, require throws ContractViolationException and calls
 * @see MessageFormat#format
 *
 * @author neopragma
 * @since 1.8
 */
final public class Contract {

    private Contract() {}

    /**
     * Asserts a precondition.
     * <p>
     * If the assertion fails, throws unchecked {@link ContractViolationException}
     * with the message passed in.
     * <p>
     * @param expression boolean expression defining the precondition
     * @param message message text to be included in the exception message text
     */
    public static void require(boolean expression, String message) {
        if (expression) return;
        int stackDepth = findFirstStackEntryThatIsNotUs();
        throw new ContractViolationException(
                MessageFormat.format("In {0}.{1}: {2}",
                        getClassName(stackDepth),
                        getMethodName(stackDepth),
                        message));
    }

    /**
     * Asserts a postcondition.
     * <p>
     * If the assertion fails, throws unchecked {@link ContractViolationException}
     * with the message passed in.
     * <p>
     * @param expression boolean expression defining the postcondition
     * @param message message text to be included in the exception message text
     */
    public static void ensure(boolean expression, String message) {
        require(expression, message);
    }

    private static String getClassName(int stackDepth) {
        return Thread.currentThread().getStackTrace()[stackDepth].getClassName();
    }

    private static String getMethodName(int stackDepth) {
        return Thread.currentThread().getStackTrace()[stackDepth].getMethodName();
    }

    private static int findFirstStackEntryThatIsNotUs() {
        for (int i = 2; i < 8; i++) {
            if (getClassName(i).endsWith(Contract.class.getSimpleName())) {
                continue;
            }
            return i-1;
        }
        return 2;
    }
}
