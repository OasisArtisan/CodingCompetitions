# https://csacademy.com/ieeextreme-practice/task/lemons/
# Score: 100/100 (76 ms - 4324 KB)

from math import ceil
N, M, S = map(int, input().split())

time = 0
pump = 1
L = 1
H = N
while True:
    pump = ceil((H + L) / 2)

    if pump == L:
        break
    time = time + (pump - L) * M + S
    L = pump

print(time)