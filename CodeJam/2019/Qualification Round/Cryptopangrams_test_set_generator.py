import numpy as np
import sys
primes =[]
with open("Cryptopangrams_primes10000.txt") as f:
    for line in f:
        primes.append(int(line))

def print_test_set():
    # Choose the 26 primes that we will use
    to_use = np.random.choice(primes,26,replace=False)
    # Add some duplicates since (25 <= L <= 100
    more = np.random.choice(to_use,np.random.randint(0,76),replace=True)
    to_use = np.concatenate((to_use, more))
    # Randomize the last result
    to_use = np.random.choice(to_use,len(to_use),replace=False)
    out = []
    for i in range(len(to_use) - 1):
        out.append(to_use[i]*to_use[i+1])
    print("{} {}".format(int(np.max(out)/2)+1, len(out)))
    out = [str(x) for x in out]
    print(' '.join(out))

cases = int(input("Cases ?"))
sys.stdout = open('test_set_100000.txt','w')
print(cases)
for _ in range(cases):
    print_test_set()
