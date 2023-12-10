package org.example.ChiSquareCriterion

import org.apache.commons.math3.distribution.ChiSquaredDistribution
import org.apache.commons.math3.stat.inference.ChiSquareTest

class ChiSquare {
    static void main(String[] args) {
        // Имеем таблицу сопряженности
        //          |   Чтение  |  Музыка | Спорт
        // -------------------------------------
        // Мужчины  |     20    |   30    |  50
        // Женщины  |     40    |   30    |  30

        // Задаем уровень значимости (alpha)
        def alpha = 0.01

        // 1. Формируем таблицу сопряженности
        long[][] observedData1 = [[20, 30, 50],  // Мужчины
                                 [40, 30, 30]]   // Женщины

        // 2. Применяем критерий хи-квадрат
        ChiSquareTest chiSquareTest = new ChiSquareTest()
        double chiSquareStat = chiSquareTest.chiSquare(observedData1)

        // 3. Определение степеней свободы
        // (количество строк - 1) * (количество столбцов - 1)
        int degreesOfFreedom = (observedData1.length - 1) * (observedData1[0].length - 1);

        // 4. Определение критического значения
        def chiSquareDistribution = new ChiSquaredDistribution(degreesOfFreedom)
        def criticalValue = chiSquareDistribution.inverseCumulativeProbability(1 - alpha)

        // Вывод результатов
        println "Хи-квадрат статистика: ${chiSquareStat}"
        println "Критическое значение: (${criticalValue})"

        // 5. Определение статистической значимости
        if (chiSquareStat > criticalValue) {
            println "Отвергаем нулевую гипотезу. Существует статистически значимая разница в предпочтениях между мужчинами и женщинами."
        } else {
            println "Принимаем нулевую гипотезу. Нет статистически значимой разницы в предпочтениях между мужчинами и женщинами."
        }
    }
}
