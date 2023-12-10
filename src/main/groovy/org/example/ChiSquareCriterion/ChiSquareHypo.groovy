package org.example.ChiSquareCriterion

import org.apache.commons.math3.distribution.ChiSquaredDistribution
import org.apache.commons.math3.stat.inference.ChiSquareTest

class ChiSquareHypo {
    static void main(String[] args) {
        // Имеем двор, в котором стоит 150 автомобилей
        // Предположим, что распределение  по стране-производителю (Россия, Япония, Германия, Франция, Китай) в процентах
        // 40 - 10 - 20 - 15 - 15
        // Тогда по количеству получим: 60 - 15 - 30 - 22.5 - 22.5
        // Но по факту, после подсчёта получили: 54 - 20 - 36 - 32 - 8
        // Проверить, совпадает ли теоретическое и экспериментальное распределения
        // Нулевая гипотеза - распределение соответствует теоретическому
        // Альтернатива - не совпадает

        // Задаем уровень значимости (alpha)
        def alpha = 0.01

        // 1. Формируем списки экспериментального и теоретического распределений
        def experimental = [54, 20, 36, 32, 8]
        def theoretical = [60, 15, 30, 22.5, 22.5]

        // 2. Применяем критерий хи-квадрат
        ChiSquareTest chiSquareTest = new ChiSquareTest()
        double chiSquareStat = chiSquareTest.chiSquare(theoretical as double[], experimental as long[])

        // 3. Определение степеней свободы
        int degreesOfFreedom = theoretical.size() - 1;

        // 4. Определение критического значения
        def chiSquareDistribution = new ChiSquaredDistribution(degreesOfFreedom)
        def criticalValue = chiSquareDistribution.inverseCumulativeProbability(1 - alpha)

        // Вывод результатов
        println "Хи-квадрат статистика: ${chiSquareStat}"
        println "Критическое значение: (${criticalValue})"

        // 5. Определение статистической значимости
        if (chiSquareStat > criticalValue) {
            println "Отвергаем нулевую гипотезу. Существует статистически значимая разница в теоретическом и экспериментальном распределениях."
        } else {
            println "Принимаем нулевую гипотезу. Нет статистически значимой разницы в распределениях."
        }
    }
}
