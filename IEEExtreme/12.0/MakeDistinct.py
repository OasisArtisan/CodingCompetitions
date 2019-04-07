# https://csacademy.com/ieeextreme-practice/task/make-distinct/
# Score: 50/100 (1584 ms - 36.2 MB)

import numpy as np

n = int(input())
data = list(map(int, input().split()))
data.sort()
#print(data)
groups = [[]]
targets = []
costs = []
gi = 0
for i in range(len(data)):
    if len(groups[gi]) < 1:
        groups[gi].append(data[i])
    elif data[i] - groups[gi][-1] >= 2:
        gi = gi + 1
        groups.append([data[i]])
    else:
        groups[gi].append(data[i])
# print(groups)

ops = 0
for i in range(len(groups)):
    groups[i] = np.array(groups[i])
    a = (len(groups[i]) - 1) / 2
    center = groups[i][int(np.floor(a))]
    targets.append(np.arange(int(center - np.floor(a)), int(center + np.ceil(a) + 1)))
    # Check if there is any overlapping with previous groups
    prev_target_element = targets[i - 1][-1]
    if prev_target_element >= targets[i][0]:
        # Check if it costs less to shift the current target to the right or shift previous targets to the left
        shiftamt = (prev_target_element - targets[i][0]) + 1
        target_shifted_right = targets[i] + shiftamt
        right_shift_cost = np.sum(np.abs(target_shifted_right - groups[i]))
        # Compute left shift cost
        left_shift_cost = 0
        pi = i - 1
        tmp_targets = targets.copy()
        tmp_costs = costs.copy()
        while pi > 0:
            print(pi)
            tmp_targets[pi] = targets[pi] - shiftamt
            new_cost = np.sum(np.abs(tmp_targets[pi] - groups[pi]))
            left_shift_cost = left_shift_cost + tmp_costs[pi] - new_cost
            tmp_costs[pi] = new_cost
            if left_shift_cost >= right_shift_cost or tmp_targets[pi-1][-1] < tmp_targets[pi][0]:
                break
            else:
                shiftamt = tmp_targets[pi-1][-1] - tmp_targets[pi][0] + 1
            pi = pi - 1
        if right_shift_cost < left_shift_cost:
            targets[i] = target_shifted_right
            costs[i] = right_shift_cost
        else:
            targets = tmp_targets
            costs = tmp_costs

    #print('(2)Target: {}'.format(target))
    costs.append(np.sum(np.abs(targets[i] - groups[i])))
print(np.sum(costs))