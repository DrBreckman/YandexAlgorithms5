# I. Пираты Баренцева моря
### Ограничение времени	1 секунда
### Ограничение памяти	64Mb
### Ввод	стандартный ввод или input.txt
### Вывод	стандартный вывод или output.txt
Вася играет в настольную игру «Пираты Баренцева моря», которая посвящена морским битвам. Игровое поле представляет собой квадрат из
N
×
N
клеток, на котором расположено
N
кораблей (каждый корабль занимает одну клетку).
Вася решил воспользоваться линейной тактикой, для этого ему необходимо выстроить все
N
кораблей в одном столбце. За один ход можно передвинуть один корабль в одну из четырёх соседних по стороне клеток. Номер столбца, в котором будут выстроены корабли, не важен. Определите минимальное количество ходов, необходимых для построения кораблей в одном столбце. В начале и процессе игры никакие два корабля не могут находиться в одной клетке.

## Формат ввода
В первой строке входных данных задаётся число
N
(
1
≤
N
≤
1
0
0
).
В каждой из следующих
N
строк задаются координаты корабля: сначала номер строки, затем номер столбца (нумерация начинается с единицы).

## Формат вывода
Выведите одно число — минимальное количество ходов, необходимое для построения.