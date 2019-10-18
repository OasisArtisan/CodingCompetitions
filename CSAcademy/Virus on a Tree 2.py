# https://csacademy.com/contest/archive/task/virus-on-a-tree/


class Node:
    def __init__(self, index):
        self.index = index
        self.parent = None
        self.height = None
        self.height_below = None
        self.family_size = None  # Including the node itself
        self.visited = False
        self.children = set()
        self.connections = set()

    def __str__(self):
        return "{}: Parent: {} Children: {} Connections: {} Family Size: {} lvltop: {} lvlbot: {}".format(
            self.index, self.parent, self.children, self.connections, self.family_size, self.height, self.height_below)


N, K = [int(x) for x in input().split()]
can_cut = dict()
nodes = [Node(i) for i in range(N)]
for e in range(N-1):
    inp = input().split()
    n1 = int(inp[0]) - 1
    n2 = int(inp[1]) - 1
    c = inp[2] == "1"
    nodes[n1].connections.add(n2)
    nodes[n2].connections.add(n1)
    can_cut[frozenset({n1, n2})] = c


def solve(parent, root):
    cuttables = list()
    uncuttables = 0
    if parent is None:
        children = root.connections
    else:
        children = root.connections - {parent.index}
    for child in children:
        a, b = solve(root, nodes[child])
        cuttables.extend(a)
        uncuttables += b
    if parent is None:
        cuttables.sort(reverse=True)
        target = N-K
        cuts = 0
        for f in cuttables:
            if target <= 0:
                break
            target -= f
            cuts += 1
        if target <= 0:
            print(cuts)
        else:
            print(-1)
    # print("Index: {} Uncuttables: {} Cuttables: {}".format(root.index,uncuttables, cuttables))
    if parent is not None and can_cut[frozenset({root.index, parent.index})]:
        total_sum = uncuttables + 1
        for c in cuttables:
            total_sum += c
        return [total_sum], 0
    else:
        return cuttables, uncuttables+1



solve(None, nodes[0])