# G. Ни больше ни меньше
### Ограничение времени	2 секунды
### Ограничение памяти	256Mb
### Ввод	стандартный ввод или input.txt
### Вывод	стандартный вывод или output.txt
Дан массив целых положительных чисел a длины n. Разбейте его на минимально возможное количество отрезков, чтобы каждое число было не меньше длины отрезка которому оно принадлежит. Длиной отрезка считается количество чисел в нем.

Разбиение массива на отрезки считается корректным, если каждый элемент принадлежит ровно одному отрезку.

## Формат ввода
Первая строка содержит одно целое число t (1 ≤ t ≤ 1 000) — количество наборов тестовых данных. Затем следуют t наборов тестовых данных.

Первая строка набора тестовых данных содержит одно целое число n (1 ≤ n ≤ 105) — длину массива.

Следующая строка содержит n целых чисел a1, a2, …, an (1 ≤ ai ≤ n) — массив a.

Гарантируется, что сумма n по всем наборам тестовых данных не превосходит 2 ⋅ 105.

## Формат вывода
Для каждого набора тестовых данных в первой строке выведите число k — количество отрезков в вашем разбиении.

Затем в следующей строке выведите k чисел len1, len2, …, lenk  — длины отрезков в порядке слева направо.