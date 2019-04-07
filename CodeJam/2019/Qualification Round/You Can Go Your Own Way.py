cases = int(input())
for i in range(1,cases + 1):
    grid = int(input())
    program = list(input())
    for j in range(len(program)):
        if program[j] == "E":
            program[j] = "S"
        else:
            program[j] = "E"
    print("Case #{}: {}".format(i,''.join(program)))