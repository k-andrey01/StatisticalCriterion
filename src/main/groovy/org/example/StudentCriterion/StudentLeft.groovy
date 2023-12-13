package org.example.StudentCriterion

import org.apache.commons.math3.distribution.TDistribution
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.example.MyNormalDistribution

class StudentLeft {
    static void main(String[] args) {
// Имеем двухвыборочный тест, показывающий, результаты решения теста двумя группами под разное музыкальное сопровождение
// Разберем левостороннюю альтернативу (М1 < М2)
// Нулевая гипотеза - первая композиция не хуже второй для прохождения теста
// Альтернативная - первая композиция хуже влияет на результат
        int n1 = 30
        double mean1 = 20
        double standardDeviation1 = 3
        int n2 = 30
        double mean2 = 22
        double standardDeviation2 = 3

        def group1 = MyNormalDistribution.generateNormalDistribution(n1, mean1 as double, standardDeviation1 as double)
        def group2 = MyNormalDistribution.generateNormalDistribution(n2, mean2 as double, standardDeviation2 as double)

        println group1
        println group2

// Задаем уровень значимости (alpha)
        def alpha = 0.05

// Определение степеней свободы
        def degreesOfFreedom = group1.size() + group2.size() - 2

// Вычисление t-статистики
        def tFirstMultDenom = Math.sqrt((group1.size() * Math.pow(standardDeviation1, 2)) + (group2.size() * Math.pow(standardDeviation2, 2)))
        def tSecondMult = Math.sqrt((group1.size() * group2.size() * degreesOfFreedom) / (group1.size() + group2.size()))

        def tStat = (mean1 - mean2) * tSecondMult / tFirstMultDenom

// Определение критической области
// Создание объекта, представляющего t-распределение
        def tDistribution = new TDistribution(degreesOfFreedom)
// Определяем критическое значение в этом распределении
// В контексте левостороннего теста мы берем вероятность alpha
        def criticalValueLeft = tDistribution.inverseCumulativeProbability(alpha)

// Вывод результатов
        println "T-статистика: ${tStat}"
        println "Критическая область (левосторонний тест): (-INF, ${criticalValueLeft})"

// 6. Определение статистической значимости
        if (tStat < criticalValueLeft) {
            println "Отвергаем нулевую гипотезу. Первая композиция хуже влияет на результаты теста."
        } else {
            println "Принимаем нулевую гипотезу. Первая композиция не хуже второй для прохождения теста."
        }
    }
}
