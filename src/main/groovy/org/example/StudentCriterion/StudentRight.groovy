package org.example.StudentCriterion

import org.apache.commons.math3.distribution.TDistribution
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

class StudentRight {
    static void main(String[] args) {
// Имеем двухвыборочный тест, показывающий, результаты решения теста двумя группами под разное музыкальное сопровождение
// Разберем правостороннюю альтернативу (М1 > М2)
// Нулевая гипотеза - первая композиция не лучше первой для прохождения теста
// Альтернативная - первая композиция лучше влияет второй результат
        double[] group1 = [26, 22, 23, 26, 20, 22, 26, 25,
                           24, 21, 23, 23, 19, 29, 22]
        double[] group2 = [18, 23, 21, 20, 20, 28, 20, 16,
                           20, 26, 21, 25, 17, 18, 19]

// Задаем уровень значимости (alpha)
        def alpha = 0.05

// 1. Вычисление средних значений для каждой группы
        def meanGroup1 = new DescriptiveStatistics(group1).getMean()
        def meanGroup2 = new DescriptiveStatistics(group2).getMean()

// 2. Вычисление дисперсий для каждой группы
        def varianceGroup1 = new DescriptiveStatistics(group1).getVariance()
        def varianceGroup2 = new DescriptiveStatistics(group2).getVariance()

// 3. Определение степеней свободы
        def degreesOfFreedom = group1.size() + group2.size() - 2

// 4. Вычисление t-статистики
        def tFirstMultDenom = ((group1.size() - 1) * varianceGroup1) + ((group2.size() - 1) * varianceGroup2);
        def tSecondMult = Math.sqrt((group1.size() * group2.size() * degreesOfFreedom) / (group1.size() + group2.size()))
        def tStat = (meanGroup1 - meanGroup2) * tSecondMult / tFirstMultDenom

// 5. Определение критической области (для правостороннего теста)
// Создание объекта, представляющего t-распределение
        def tDistribution = new TDistribution(degreesOfFreedom)
// Определяем критическое значение в этом распределении
// В контексте правостороннего теста мы берем вероятность 1 - alpha
        def criticalValueTwoSided = tDistribution.inverseCumulativeProbability(1 - alpha)

// Вывод результатов
        println "T-статистика: ${tStat}"
        println "Критическая область (двусторонний тест): (${criticalValueTwoSided}, +INF)"


// 6. Определение статистической значимости
        if (tStat > criticalValueTwoSided) {
            println "Отвергаем нулевую гипотезу. Первая композиция лучше влияет второй результат."
        } else {
            println "Принимаем нулевую гипотезу. Первая композиция не лучше первой для прохождения теста."
        }
    }
}