package org.example.StudentCriterion

import org.apache.commons.math3.distribution.TDistribution
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

class Student {
    static void main(String[] args) {
// Имеем двухвыборочный тест, показывающий, результаты решения теста двумя группами под разное музыкальное сопровождение
// Разберем двухстороннюю альтернативу (М1 != М2)
// Нулевая гипотеза - музыкальное сопровождение не влияет на результат
// Альтернативная - влияет (имеем дело с двухсторонней критической областью)
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

// 5. Определение критической области (для двустороннего теста)
// Создание объекта, представляющего t-распределение
        def tDistribution = new TDistribution(degreesOfFreedom)
// Определяем критическое значение в этом распределении
// В контексте двустороннего теста мы берем часть из общей вероятности (1 - alpha) для каждого хвоста распределения, поэтому (1 - alpha / 2)
        def criticalValueTwoSided = tDistribution.inverseCumulativeProbability(1 - alpha / 2)
// Если бы была левостороння область, брали бы просто от alpha, если бы право-, то от 1-Alpha

// Вывод результатов
        println "T-статистика: ${tStat}"
        println "Критическая область (двусторонний тест): (-${criticalValueTwoSided}, ${criticalValueTwoSided})"

// 6. Определение статистической значимости
        if (Math.abs(tStat) > criticalValueTwoSided) {
            println "Отвергаем нулевую гипотезу. Существует статистическое различие в способности решения задач под разными типами музыки."
        } else {
            println "Принимаем нулевую гипотезу. Нет статистического различия в способности решения задач под разными типами музыки."
        }
// Для лево- и правосторонних без модуля, просто меньше или больше
    }
}