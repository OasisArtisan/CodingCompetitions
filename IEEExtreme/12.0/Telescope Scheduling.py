# https://csacademy.com/ieeextreme-practice/task/telescope-scheduling/
# Score: 0/100 (20950 ms - 256 MB)

import numpy as np
n = int(input())

data = []

for i in range(n):
    d = tuple(map(int,input().split()))
    data.append(d)

#star[0] >= interval[0] and star[0] <= interval[1] or star[1] >= interval[0] and star[1] <= interval[1]:
combinations = []
for star in data:
    if len(combinations) == 0:
        combinations.append([[star], star[2]])
        continue
    combinations_to_add = []
    for comb in combinations:
        overlapping_stars = []
        for st in comb[0]:
            if star[0] >= st[0] and star[0] <= st[1] or star[1] >= st[0] and star[1] <= st[1]:
                overlapping_stars.append(st)
            print(overlapping_stars)
        if len(overlapping_stars) == 0:
            comb[0].append(star)
            comb[1] += star[2]
        else:
            new_comb = [list(comb[0]),comb[1]]
            for s in overlapping_stars:
                new_comb[0].remove(s)
                new_comb[1] -= s[2]
            new_comb[0].append(star)
            new_comb[1] += star[2]
            combinations_to_add.append(new_comb)
    combinations = combinations + combinations_to_add

print(max(combinations,key=lambda x: x[1])[1])