import numpy as np
np.set_printoptions(linewidth=1000)
alphabet = list("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
# We should improve this performance using some fancy mathematical approach
def get_first_divider(n):
    for i in range(2, int(np.sqrt(n)) + 1):
        if(n%i == 0):
            return i
    return 1

output = []
cases = int(input())
for case in range(1, cases + 1):
    # Take input
    N, val_len = [int(x) for x in input().split()]
    values = np.array([int(x) for x in input().split()])

    # Find the first prime of the smallest number in the values array
    min_i = np.argmin(values)
    min_v = values[min_i]
    first_prime = get_first_divider(min_v)
    primes = np.zeros(val_len + 1)
    primes[min_i] = first_prime
    primes[min_i+1] = values[min_i] / first_prime
    def expand():
        for i in range(min_i + 1, val_len):
            if values[i] % primes[i] != 0:
                return False
            primes[i + 1] = values[i] / primes[i]
        for i in range(min_i, 0, -1):
            if values[i] % primes[i] != 0:
                return False
            primes[i-1] = values[i-1] / primes[i]
        return True
    if not expand():
        temp = primes[min_i]
        primes[min_i] = primes[min_i+1]
        primes[min_i+1] = temp
        if not expand():
            raise ValueError()
    # Now we have all of our primes in order
    # We now sort them and associate them with alphabet
    primes_sorted = np.sort(list(set(primes)))
    translator = dict()
    for i in range(len(alphabet)):
        translator[primes_sorted[i]] = alphabet[i]
    translated_text = []
    for prime in primes:
        translated_text.append(translator[prime])
    output.append("Case #{}: {}".format(case, ''.join(translated_text)))
for line in output:
    print(line)

