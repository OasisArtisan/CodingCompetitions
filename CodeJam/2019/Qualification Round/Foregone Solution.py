cases = int(input())
for i in range(1,cases + 1):
    N = input()
    a = list(N)
    b = ["0" for _ in range(len(a))]
    for j in range(len(N)):
        if a[j] == "4":
            a[j] = "3"
            b[j] = "1"
    print("Case #{}: {} {}".format(i,int(''.join(a)),int(''.join(b))))