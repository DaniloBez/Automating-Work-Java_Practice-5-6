import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тестування арифметичних операцій калькулятора")
public class CalculatorTest {
    Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    @Tag("positive")
    @DisplayName("Додавання двох додатних чисел")
    void shouldAddNumbers() {
        double result = calculator.add(2, 2);

        assertEquals(4, result, "Помилка: 2 + 2 має дорівнювати 4");
    }

    @Test
    @Tag("positive")
    @DisplayName("Додавання двох від'ємних чисел")
    void shouldAddNegativeNumbers() {
        double result = calculator.add(-2, -2);
        assertEquals(-4, result, "Помилка: -2 + (-2) має дорівнювати -4");
    }

    @Tag("positive")
    @DisplayName("Додавання нуля не змінює вихідне число")
    @ParameterizedTest(name = "Тест {index}: {0} + 0 = {0}")
    @ValueSource(doubles = {2, 3, 4, 5, -3, 100, -1234, 0.846182})
    void shouldReturnSameNumberWhenAddZero(double expected) {
        double result = calculator.add(expected, 0);

        assertEquals(expected, result, "Помилка: додавання нуля до " + expected + " змінило результат");
    }

    @Test
    @Tag("positive")
    @DisplayName("Віднімання двох додатних чисел")
    void shouldSubtractNumbers() {
        double result = calculator.sub(5, 3);

        assertEquals(2, result, "Помилка: 5 - 3 має дорівнювати 2");
    }

    @Test
    @Tag("positive")
    @DisplayName("Віднімання двох від'ємних чисел")
    void shouldSubtractNegativeNumbers() {
        double result = calculator.sub(-5, -2);

        assertEquals(-3, result, "Помилка: -5 - (-2) має дорівнювати -3");
    }

    @Tag("positive")
    @DisplayName("Перевірка множення з різними параметрами")
    @ParameterizedTest(name = "Множення: {0} * {1} = {2}")
    @CsvSource({
            "2, 3, 6",
            "-2, -3, 6",
            "5, 0, 0",
            "-4, 2.5, -10",
            "1, 100.5, 100.5"
    })
    void shouldMultiplyNumbers(double a, double b, double expected) {
        double result = calculator.mul(a, b);
        assertEquals(expected, result, String.format("Помилка множення: %.1f * %.1f не дорівнює %.1f", a, b, expected));
    }

    @TestFactory
    @Tag("positive")
    @DisplayName("Динамічна генерація тестів для ділення")
    Stream<DynamicTest> shouldDivideNumbersExcludeDivideByZeroTestFactory() {
        Calculator calculator = new Calculator();
        Random random = new Random();

        return IntStream.rangeClosed(1, 10).mapToObj( i -> {
            double a = random.nextInt(100);
            double b = random.nextInt(4);

            String testName = String.format("Тест %d: Ділення %.0f / %.0f", i, a, b);

            return DynamicTest.dynamicTest(testName, () -> {
                Assumptions.assumeTrue(b != 0, "Дільник згенерувався як 0. Пропускаємо цей випадок.");

                double expected = a / b;
                double result = calculator.div(a, b);

                assertEquals(expected, result, 0.0001, String.format("Помилка логіки ділення: очікувалося %.4f, але отримано %.4f", expected, result));
            });
        });
    }

    @Test
    @Tag("negative")
    @DisplayName("Ділення на нуль має викидати ArithmeticException")
    void shouldThrowExceptionWhenDivideByZero() {
        assertThrows(
                ArithmeticException.class,
                () -> calculator.div(5, 0),
                "Помилка: калькулятор дозволив ділення на нуль!"
        );
    }
}
