# https://csacademy.com/contest/archive/task/flip-game/statement/

import numpy as np

N, M = [int(x) for x in input().split()]
mat = np.ndarray((N,M), dtype=np.bool)
ans = N* 2**(M-1)
for i in range(N):
    b = input().split()
    if b[0] == "0":
        mat[i] = [x == "0" for x in b]
    else:
        mat[i] = [x == "1" for x in b]
for i in range(1,M):
    ones = np.sum(mat[:,i])
    if ones <= N//2:
        ans += (N-ones)*2**(M-1-i)
    else:
        ans += ones*2**(M-1-i)
print(ans)