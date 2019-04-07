import sys


class Division:
    def __init__(self, first, N, B):
        self.first = first
        self.N = N
        self.B = B
        self.right_div = None
        self.left_div = None
        self.broken_indices = None
        if B == 0:
            self.broken_indices = []
        elif N == B:
            self.broken_indices = list(range(first, first+N))

    def get_test(self):
        if self.is_finished():
            return ['0' for _ in range(self.N)]
        nl = int(self.N/2)
        nr = self.N - nl
        return ['0' for _ in range(nl)] + ['1' for _ in range(nr)]

    def notify_result(self, result):
        assert not self.is_finished()
        ones = 0
        for r in result:
            if r == '1':
                ones += 1
        nl = int(self.N/2)
        nr = self.N - nl
        right_errors = nr-ones
        left_errors = self.B - right_errors
        self.left_div = Division(self.first, nl, left_errors)
        self.right_div = Division(self.first + nl,nr, right_errors)

    def is_finished(self):
        return self.broken_indices is not None


cases = int(input())
for case in range(1, cases + 1):
    N,B,F = [int(x) for x in input().split()]
    broken_indices = []
    divs = [Division(0, N, B)]
    processing = True
    while F > 0:
        test_out = []
        finished = True
        for div in divs:
            test_out += div.get_test()
            if not div.is_finished():
                finished = False
        if finished:
            break
        # print("TEST_STORE {}".format(''.join(test_out)))
        print(''.join(test_out))
        sys.stdout.flush()
        response = input()
        divs_expanded = []
        ri = 0
        for div in divs:
            #print("{}:{}--{}".format(div.first,div.first+div.N,div.broken_indices))
            if div.is_finished(): # No expansion needed
                divs_expanded.append(div)
            else:
                div.notify_result(response[ri:(ri + div.N-div.B)])
                divs_expanded.append(div.left_div)
                divs_expanded.append(div.right_div)
            ri += div.N-div.B
        #print()
        divs = divs_expanded
    for div in divs:
        broken_indices += div.broken_indices
    broken_indices = [str(x) for x in broken_indices]
    print(' '.join(broken_indices))
    sys.stdout.flush()
    verdict = input()
    print(' '.join(broken_indices), file=sys.stderr)
    print(verdict,file=sys.stderr)


